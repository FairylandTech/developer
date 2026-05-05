/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 04:20:00 UTC+08:00
 ****************************************************/
package host.fairy.application.convert.example;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.domain.model.example.User;
import org.springframework.stereotype.Component;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Component
public class UserApplicationConverter {
    public User toModel(UserDO userDO) {
        if (userDO == null) {
            return null;
        }
        return User.builder()
                .id(userDO.getId())
                .username(userDO.getUsername())
                .password(userDO.getPassword())
                .phone(userDO.getPhone())
                .info(userDO.getInfo())
                .status(userDO.getStatus())
                .balance(userDO.getBalance())
                .createdAt(userDO.getCreatedAt())
                .updatedAt(userDO.getUpdatedAt())
                .enabled(userDO.getEnabled())
                .build();
    }
    
    public UserDO toDO(User user) {
        if (user == null) {
            return null;
        }
        
        return UserDO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .phone(user.getPhone())
                .info(user.getInfo())
                .status(user.getStatus())
                .balance(user.getBalance())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .enabled(user.getEnabled())
                .build();
    }
}
