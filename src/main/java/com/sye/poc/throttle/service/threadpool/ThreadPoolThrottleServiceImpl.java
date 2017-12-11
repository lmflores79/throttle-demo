/***********************************************************************************************************************
 * FileName - ThreadPoolThrottleServiceImpl.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.service.threadpool;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.ThreadContext;
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
@Component("threadThrottlingService")
public class ThreadPoolThrottleServiceImpl implements ThrottleService {

    // Instantiate the ExecutorService with the maximum number of concurrent tasks desired
    private static ExecutorService fixedExecutorServie = Executors.newFixedThreadPool(Constants.MAX_CONCURRENT_OPERATIONS);
    
    @Autowired
    private Command command;
    
    
    @Override
    public String throttle(String param) {
        Task task = new Task(param);
        
        // Submit a task. If the maximum number of tasks has been reached, it will be queued. 
        Future<String> futureTask = fixedExecutorServie.submit(task);
            try {
                return futureTask.get(2, TimeUnit.MINUTES);
            } catch (InterruptedException | ExecutionException
                    | TimeoutException e) {
                futureTask.cancel(true);
                log.error("Error for task with param {}", param, e);
                throw new ThrottleServiceException(e);
            }
    }
    
    
    private class Task implements Callable<String>{

        private Map<String, String> context = MapUtils.EMPTY_MAP;
        private String message;
        
        public Task(String message) {
            this.message = message;
            context = ThreadContext.getImmutableContext();
        }
        
        @Override
        public String call() {
            ThreadContext.putAll(context);
            log.info("Entering request with param {}", message);
            String response = command.test(message);
            log.info("Finishing request with result {}", message);
            return response;
        }
    }

}
