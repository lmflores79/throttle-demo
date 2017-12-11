/***********************************************************************************************************************
 * FileName - DistributedThrottleServiceImpl.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import  com.sye.poc.throttle.service.ThrottleService;

/**
 * 
 * @author luis flores soberon
 *
 */
@Log4j2
@Component("distributedThrottlingService")
public class DistributedThrottleServiceImpl implements ThrottleService {


    @Autowired
    private Producer producer;
    
    @Value("${queue}")
    String queue;
    
    @Override
    public String throttle(String param) {
        log.info("Entering request with param {}", param);
        String response = producer.sendTo(queue, param);
        log.info("Exiting request with param {}", param);
        return "Response= " + response;
    }
    
}
