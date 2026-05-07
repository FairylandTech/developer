/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-06 05:50:44 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.http.config;

import host.fairy.interfaces.http.interceptor.AuthInterceptor;
import host.fairy.interfaces.http.interceptor.RequestAuditInterceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    private final RequestAuditInterceptor requestAuditInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/error");
        
        registry.addInterceptor(requestAuditInterceptor)
                .addPathPatterns("/**");
    }
}
