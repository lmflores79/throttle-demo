/***********************************************************************************************************************
 * FileName - ThrottleServiceException.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.exceptions;

/**
 * 
 * @author luis flores soberon
 *
 */
public class ThrottleServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ThrottleServiceException() {
        super();
    }

    public ThrottleServiceException(String error) {
        super(error);
    }

    public ThrottleServiceException(Throwable e) {
        super(e);
    }
    
}
