/***********************************************************************************************************************
 * FileName - RabbitConfiguration.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * Enable Spring Boot RabbitMQ auto-configuration.
 * @author luis flores soberon
 *
 */
@Profile("rabbit")
@Configuration 
@ImportAutoConfiguration(RabbitAutoConfiguration.class)
public class RabbitConfiguration {

    @Value("${queue}")
    private String queue;

    @Bean
    Queue queue(){
        return new Queue(queue,false);
    }    

}
