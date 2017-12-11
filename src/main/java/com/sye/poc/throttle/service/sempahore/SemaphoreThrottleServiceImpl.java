/***********************************************************************************************************************
 * FileName - SemaphoreThrottleServiceImpl.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.service.sempahore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import  com.sye.poc.throttle.exceptions.ThrottleServiceException;
import  com.sye.poc.throttle.service.ThrottleService;
import  com.sye.poc.throttle.service.commands.Command;
import  com.sye.poc.throttle.service.utils.Constants;


/**
 * 
 * @author luis flores soberon
 *
 */
@Log4j2
@Component("semaphoreThrottlingService")
public class SemaphoreThrottleServiceImpl implements ThrottleService {

    @Autowired
    private Command command;
    
    private Semaphore semaphore = new Semaphore(Constants.MAX_CONCURRENT_OPERATIONS);
    
    @Override
    public String throttle(String param) {

        try {
            semaphore.tryAcquire(10, TimeUnit.SECONDS);
            log.info("Entering request with param {}", param);
            String response = command.test(param);
            log.info("Exiting request with param {}", param);
            return response;
        } catch (InterruptedException e) {
            throw new ThrottleServiceException("Test");
        } finally {
            semaphore.release();
        }
        
    }
}
