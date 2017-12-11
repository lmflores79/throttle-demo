/***********************************************************************************************************************
 * FileName - Constants.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.service.utils;

/**
 * 
 * @author luis flores soberon
 *
 */
public final class Constants {

    public static final int MAX_CONCURRENT_OPERATIONS = 2;
    
    // As in this demo we are simulating response times between 2 and 5 seconds, we are setting this 
    // to 6 seconds
    public static final String DEFAULT_TIME_OUT_IN_MILLISECONDS = "6000";
    
    private Constants() {
        
    }
}
