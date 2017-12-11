/***********************************************************************************************************************
 * FileName - ThrottleDemoApplication.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author luis flores soberon
 *
 */
@EnableAsync
@EnableScheduling
@EnableCircuitBreaker
@EnableHystrixDashboard
@SpringBootApplication(scanBasePackages={" com.sye.poc.throttle"}, exclude=RabbitAutoConfiguration.class)
public class ThrottleDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThrottleDemoApplication.class, args);
	}
}
