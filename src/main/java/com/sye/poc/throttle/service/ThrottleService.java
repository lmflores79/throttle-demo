/***********************************************************************************************************************
 * FileName - ThrottleService.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.service;

import org.springframework.stereotype.Component;


/**
 * 
 * @author luis flores soberon
 *
 */
@Component
public interface ThrottleService {

    /**
     * 
     * @param param
     * @return
     */
    public String throttle(String param);
}
