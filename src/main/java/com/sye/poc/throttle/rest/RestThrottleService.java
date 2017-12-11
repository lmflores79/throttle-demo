/***********************************************************************************************************************
 * FileName - RestThrottleService.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

/**
 * 
 * @author luis flores soberon
 *
 */
@Path("/throttle")
@Service
@Produces(MediaType.APPLICATION_JSON)
public interface RestThrottleService {

    /**
     * Example throttle endpoint using a simple semaphore.
     * @return
     */
    @GET
    @Path("/semaphore/{param}")
    public Response semaphoreThrottling(@PathParam("param") String param);
    
    
    /**
     * Example throttle endpoint using an MQ server supporting distributed work across multiple consumers.
     * @return
     */
    @GET
    @Path("/distributed/{param}")
    public Response distributedThrottle(@PathParam("param") String param);

    /**
     * Example throttle endpoint using a simple fixed thread pool.
     * @return
     */
    @GET
    @Path("/threaded/{param}")
    public Response threadPoolThrottle(@PathParam("param") String param);

}
