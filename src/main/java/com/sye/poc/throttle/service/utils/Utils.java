/***********************************************************************************************************************
 * FileName - Utils.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.service.utils;

import java.util.Random;

/**
 * 
 * @author luis flores soberon
 *
 */
public class Utils {

    
    private Utils() {
        
    }
    
    /**
     * Generates a random number between 2 and 5 for simulating a random delay.
     * @return - A Random number between 2 and 5.
     */
    public static int generateRandomLenght() {
        Random r = new Random();
        int low = 2000;
        int high = 5000;
        return r.nextInt(high - low) + low;
    }

}
