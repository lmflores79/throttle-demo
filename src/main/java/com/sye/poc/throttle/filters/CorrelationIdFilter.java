/***********************************************************************************************************************
 * FileName - CorrelationIdFilter.java
 *
 *  
 *
 * $Author$
 * $Revision$
 * $Change$
 * $Date$
 **********************************************************************************************************************/
package  com.sye.poc.throttle.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

/**
 * Simple filter that ensures a correlation id is in place for logging tracking purposes. 
 * @author luis flores soberon
 *
 */
@Component
public class CorrelationIdFilter implements Filter{

    private static final String CORRELATION_ID_KEY = "correlationId";
    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // Honor any potential correlationId already passed by the client. If there is no one, create one.
        String correlationId = ((HttpServletRequest) request).getHeader(CORRELATION_ID_HEADER);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = UUID.randomUUID().toString();
            ((HttpServletResponse)response).setHeader(CORRELATION_ID_HEADER, correlationId);
        }
        
        ThreadContext.put(CORRELATION_ID_KEY, correlationId);
        try{
            chain.doFilter(request, response);
        } finally {
            // Make sure to clear the context when finished.
            ThreadContext.remove(CORRELATION_ID_KEY);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

}
