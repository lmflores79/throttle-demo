/***********************************************************************************************************************
 * FileName - Producer.java
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

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import  com.sye.poc.throttle.exceptions.ThrottleServiceException;

/**
 * 
 * @author luis flores soberon
 *
 */
@Component
@Log4j2
public class Producer {

    @Autowired(required=false)
    private RabbitTemplate rabbitTemplate;
    
    public String sendTo(String routingkey,String message){
        if (rabbitTemplate != null) {
            String response = (String)this.rabbitTemplate.convertSendAndReceive(routingkey,message);
            log.info("Sent-> ...{}, Received: {}", message, response);
            return response;
        }
        
        throw new ThrottleServiceException("Operation not supported. Enable rabbit spring profile.");
    }    
}
