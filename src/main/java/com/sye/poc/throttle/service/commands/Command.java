/***********************************************************************************************************************
 * FileName - Command.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.service.commands;

import org.springframework.stereotype.Component;

import  com.sye.poc.throttle.exceptions.ThrottleServiceException;
import  com.sye.poc.throttle.service.utils.Utils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


/**
 * Command encapsulating the job we want to throttle.
 * It is wrapped in a Hystrix command so we can monitor and have insight about the number of threads
 * executed while testing.
 *  
 * @author luis flores soberon
 *
 */
@Component
public class Command {

    @HystrixCommand(commandKey="ExampleCommand", groupKey="ExampleGroupKey")
    public String test(String command) {
        try {
            Thread.sleep(Utils.generateRandomLenght());
        } catch (InterruptedException e) {
            throw new ThrottleServiceException(e);
        }        
        return "Processed " + command;
    }
}
