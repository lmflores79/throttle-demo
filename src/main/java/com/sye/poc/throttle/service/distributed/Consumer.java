/***********************************************************************************************************************
 * FileName - Consumer.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.service.distributed;

import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import  com.sye.poc.throttle.service.commands.Command;

/**
 * 
 * @author luis flores soberon
 *
 */
@Profile("rabbit")
@Component
@Log4j2
public class Consumer {

    @Autowired
    private Command command;
    
    @RabbitListener(queues="${queue}")
    public String handler(String message){
        log.info("Consuming: " + message);
        String commandResponse = command.test(message); 
        return commandResponse;
    }
}
