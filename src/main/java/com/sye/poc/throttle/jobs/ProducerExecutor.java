/***********************************************************************************************************************
 * FileName - ProducerExecutor.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.jobs;

import java.util.Date;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import  com.sye.poc.throttle.service.ThrottleService;

/**
 * 
 * @author luis flores soberon
 *
 */
@Component
@Log4j2
public class ProducerExecutor {

    @Autowired
    @Qualifier("semaphoreThrottlingService")
    private ThrottleService semaphoreThrottlingService;
    
    @Autowired
    @Qualifier("distributedThrottlingService")
    private ThrottleService distributedThrottlingService;
    
    @Autowired
    @Qualifier("threadThrottlingService")
    private ThrottleService threadPoolThrottlingService;

    @Async
    public void executeDistributedThrottlingRequest() {
        distributedThrottlingService.throttle(new Date().toString());
    }
    
    
    @Async
    public void executeSemaphoreThrottlingRequest() {
        semaphoreThrottlingService.throttle(new Date().toString());
    }
    
    
    @Async
    public void executeThreadThrottlingRequest() {
        semaphoreThrottlingService.throttle(new Date().toString());
    }      
}
