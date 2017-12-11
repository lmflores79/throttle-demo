/***********************************************************************************************************************
 * FileName - RestThrottleServiceImpl.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.rest.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import  com.sye.poc.throttle.rest.RestThrottleService;
import  com.sye.poc.throttle.service.ThrottleService;

/**
 * 
 * @author luis flores soberon
 *
 */
@Component
public class RestThrottleServiceImpl implements RestThrottleService{

    @Autowired
    @Qualifier("semaphoreThrottlingService")
    private ThrottleService semaphoreThrottlingService;
    
    @Autowired
    @Qualifier("distributedThrottlingService")
    private ThrottleService distributedThrottlingService;
    
    @Autowired
    @Qualifier("threadThrottlingService")
    private ThrottleService threadPoolThrottlingService;
    
    @Override
    public Response semaphoreThrottling(String param) {
        return throttle(semaphoreThrottlingService, param);
    }

    @Override
    public Response distributedThrottle(String param) {
        return throttle(distributedThrottlingService, param);
    }

    @Override
    public Response threadPoolThrottle(String param) {
        return throttle(threadPoolThrottlingService, param);
    }
    
    private Response throttle(ThrottleService throttleService, String param) {
        String response = throttleService.throttle(param);
        return Response.ok(response).build();
    }
    

    
}
