/***********************************************************************************************************************
 * FileName - JobScheduler.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class to test the distributed implementation.
 * 
 * It needs to be run with the scheduler spring profile and it will produce
 * a message each 500 milliseconds to RabbitMQ through {@link ProducerExecutor}.
 * 
 * @author luis flores soberon
 *
 */
@Profile("scheduler")
@Component
public class JobScheduler {

    @Autowired
    private ProducerExecutor producerExecutor;


    
    @Scheduled(fixedRate = 1000L)
    public void executeDistributedTest(){
        producerExecutor.executeDistributedThrottlingRequest();
    }
    
  /*  
    @Scheduled(fixedRate = 2000L)
    public void executeSempahoreTest(){
        producerExecutor.executeSemaphoreThrottlingRequest();
    }
    
    //@Scheduled(fixedRate = 2000L)
    public void executeThreadedTest(){
        producerExecutor.executeThreadThrottlingRequest();
    }    
    */
    
}
