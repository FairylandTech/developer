/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-06 06:23:47 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.http.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@Component
public class RequestAuditInterceptor implements HandlerInterceptor {
    private static final String AUDIT_STATT_TIME = "audit-start-time";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String handlerClassName = handlerMethod.getBeanType().getName();
        String handlerMethodName = handlerMethod.getMethod().getName();
        log.info("RequestAuditInterceptor: {} {} -> {}.{}", method, requestURI, handlerClassName, handlerMethodName);
        
        request.setAttribute(AUDIT_STATT_TIME, System.currentTimeMillis());
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        long startTime = (long) request.getAttribute(AUDIT_STATT_TIME);
        long duration = System.currentTimeMillis() - startTime;
        log.info("RequestAuditInterceptor: {} {} completed in {} ms", request.getMethod(), request.getRequestURI(), duration);
    }
}
