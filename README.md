# throttle-demo

Example of a REST service that acts as a throttle mechanism for executing a given task. This task could be communicating with an external system or any other thing. For our testing purposes, it is a simple **Command** class which randomly generates a delay between 2 and 5 seconds to simulate latency on the given task. (For a more extensive explanation in Spanish, please visit http://www.tecnohobby.net/ppal/index.php/programacion/java/38-control-de-trafico-en-java-rate-limit-throttling-mechanism)

The service shows three mechanisms for performing the throttling and provides one endpoint to test each of them:

* A Semaphore
* A fixed size ThreadPoolExecutor
* A Broker with a fixed set of consumers


The following is a class diagram depicting the main classes involved:

![Class Diagram](http://www.tecnohobby.net/ppal/images/stories/news/Programacion/Java/throttle-class-3.jpg)

## Semaphore

This mechanism uses a simple **java.util.concurrent.Semaphore** which acts as both, a queueing mechanism and a semaphore regulating the traffic. A single instance of the semaphore is instantiated at start up time with a given number of permits. When the service receives a request, it tries to acquire a permit decreasing the number of available them and when it finishes processing the task, it releases it increasing the number of available permits. When a thread tries to acquire a permit when there are no more available, it will be queued and will wait until one is released or a certain time passes in which case it will expire. The advantage of this method is that is extremely simple to implement but the disadvantage is that the number of permits is set at instantiation time so, to replace it at runtime, we would need to figure out a way to replace the instance. The main class to explore the implementation details of this mechanism would be **SemaphoreThrottleServiceImpl**.

## Fixed size ThreadPoolExecutor

This mechanism uses an **ExecutorService** created using **Executors.newFixedThreadPool(Constants.MAX_CONCURRENT_OPERATIONS);**. The ExecutorService acts as both, the queueing mechanism and the semaphore. The ExecutorService will only execute at most **Constants.MAX_CONCURRENT_OPERATIONS** tasks at any given time. If a task is submitted and there are no available threads in the pool, it will be queued until one thread is released or certain time passes in which case it will timeout. The advantage over the Semaphore option is that we don't have to explicitly acquire and release permits. The disadvantage is that now we need to handle futures or threads and also that we can't change the number of maximum concurrent threads at runtime. The main class to explore the implementation details of this mechanism would be **DistributedThrottleServiceImpl**.

## Broker with a fixed set of Consumers

This is the most sophisticated of the three options shown here. It uses an **RPC mechanism** for executing the task and receive the results. Basically, we set a given number of concurrent **Consumers** that will be listening at a given **Queue**. When a request arrives, a message is published to the **Broker** through a **Producer** and is received by one **Consumer** which will be in charge of executing the task and send back the results to the **Producer**. When all consumers are busy, any new message published will be queued and will be served only when a **Consumer** is free again. The broker acts as the queueing mechanism and the maximum number of concurrent consumers acts, in some sense, as a semaphore. The main advantage of this approach over the other two explained above is that this supports distributed processing of the task to throttle. The consumers could be running in another service in another host. The other advantage is that, at least in this example where we use **RabbitMQ** as broker and the **Spring AMQP** implementation, we can change the number of concurrent consumers at runtime. The main classes involved in this mechanism are **DistributedThrottleServiceImpl**, **Consumer** and **Producer**.

Below is an image of a sequence diagram showing what we decribed above:

![Sequence Diagram](http://www.tecnohobby.net/ppal/images/stories/news/Programacion/Java/throttle-distributed.jpg)



## Notes

- Developed in **Java 8** with **Spring Boot** and an **embedded Tomcat** listening in port 8080.
- For the REST service, it uses the **Apache CXF** implementation of the **JAX-RS** specification.
- As broker, we use **RabbitMQ** so make sure you have it up and running if the **rabbit** spring profile is enabled.
- In order to monitor the number of executing threads, the calls are wrapped using a **Hystrix** command whose metrics are shown in a Hystrix dashboard.
- The dashboard is available at  http://localhost:8080/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8080%2Fhystrix.stream%2F
- The endpoints for testing each mechanism are:
  -http://localhost:8080/throttle-service/throttle/semaphore/{param}
  -http://localhost:8080/throttle-service/throttle/threaded/{param}
  -http://localhost:8080/throttle-service/throttle/distributed/{param}
- A simple filter is included providing a simple correlationId generation mechanism for log tracking purposes.
- Distributed support is only available under **rabbit** spring profile.
- A scheduler is also provided for automatically invoking the distributed mechanism each second by using the profile **scheduler**. **(For using this profile, the rabbit profile needs to be used as well).**


## Prerequisites
* Java 8
* RabbitMQ

## Installing and Running
* Start up RabbitMQ. If you have Docker, you can run the following command: *"docker run -d --hostname my-rabbit --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:3-management"*
* Build the application with *mvn clean package*.
* Run it with distributed support: "java -jar -Dspring.profiles.active=rabbit throttle-demo-0.0.1-SNAPSHOT.jar".
* Run it with the scheduler enabled: "java -jar -Dspring.profiles.active=scheduler,rabbit throttle-demo-0.0.1-SNAPSHOT.jar".

## Example Execution

Below is an image of the dashboard for a configuration of a maximum number of 2 tasks processing 5 requests. As it can be noted, the Hystrix ThreadPool *MaxActive* and *Active* is 2 whereas the queue size is 5. This means 5 tasks are being processed (2 being processed right now and 3 waiting)

![Hystrix Dashboard](http://www.tecnohobby.net/ppal/images/stories/news/Programacion/Java/thorttle-dashboard.jpg)
