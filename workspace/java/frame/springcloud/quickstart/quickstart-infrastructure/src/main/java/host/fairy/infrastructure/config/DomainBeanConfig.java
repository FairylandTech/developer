/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 05:02:32 UTC+08:00
 ****************************************************/
package host.fairy.infrastructure.config;

import host.fairy.domain.repository.example.UserRepository;
import host.fairy.domain.service.example.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Configuration
public class DomainBeanConfig {
    
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
