/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 04:14:06 UTC+08:00
 ****************************************************/
package host.fairy.domain.service.example;

import host.fairy.domain.model.example.User;
import host.fairy.domain.repository.example.UserRepository;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import host.fairy.fairylandfuture.exception.business.BusinessExceptionBase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public User createUser(User user) {
        log.info("Step 3: Domain User -> {}", user.toString());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new BusinessExceptionBase("用户名已经存在");
        }
        
        user.setStatus(EnabledEnum.ENABLED);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setEnabled(EnabledEnum.ENABLED);
        return user;
    }
    
    public User updateUser(User user) {
        if (user.getId() == null || user.getId().equals(0L)) {
            throw new RuntimeException("Invalid user ID");
        }
        
        return user;
    }
}
