/***********************************************************************************************************************
 * FileName - ThrottleServiceExceptionMapper.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.rest.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

import  com.sye.poc.throttle.exceptions.ThrottleServiceException;

/**
 * JAX-RS Exception mapper.
 * 
 * @author luis flores soberon
 *
 */
@Provider
@Component
@Log4j2
public class ThrottleServiceExceptionMapper implements ExceptionMapper<ThrottleServiceException>{

    @Override
    public Response toResponse(ThrottleServiceException exception) {
        log.error("Error encountered: ",  exception.getMessage(),  exception);
        return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .entity(exception.getMessage())
                .build();
    }

}
