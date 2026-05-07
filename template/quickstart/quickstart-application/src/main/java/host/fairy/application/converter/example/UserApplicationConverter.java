/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 04:20:00 UTC+08:00
 ****************************************************/
package host.fairy.application.converter.example;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.domain.model.example.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Component
public class UserApplicationConverter {
    public User toModel(UserDO src) {
        if (src == null) {
            return null;
        }
        return User.builder()
                .id(src.getId())
                .username(src.getUsername())
                .password(src.getPassword())
                .phone(src.getPhone())
                .info(src.getInfo())
                .status(src.getStatus())
                .balance(src.getBalance())
                .createdAt(src.getCreatedAt())
                .updatedAt(src.getUpdatedAt())
                .enabled(src.getEnabled())
                .startTime(src.getStartTime())
                .endTime(src.getEndTime())
                .build();
    }
    
    public UserDO toDO(User src) {
        if (src == null) {
            return null;
        }
        
        return UserDO.builder()
                .id(src.getId())
                .username(src.getUsername())
                .password(src.getPassword())
                .phone(src.getPhone())
                .info(src.getInfo())
                .status(src.getStatus())
                .balance(src.getBalance())
                .createdAt(src.getCreatedAt())
                .updatedAt(src.getUpdatedAt())
                .enabled(src.getEnabled())
                .startTime(src.getStartTime())
                .endTime(src.getEndTime())
                .build();
    }
    
    public List<UserDO> toDOList(List<User> src) {
        if (src == null) {
            return null;
        }
        return src.stream().map(this::toDO).toList();
    }
}
