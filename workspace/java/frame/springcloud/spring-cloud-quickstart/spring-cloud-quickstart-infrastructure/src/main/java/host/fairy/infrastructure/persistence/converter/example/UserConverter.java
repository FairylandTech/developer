package host.fairy.infrastructure.persistence.converter.example;

import host.fairy.domain.model.example.User;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import host.fairy.infrastructure.persistence.model.example.UserMO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Component
public class UserConverter {
    
    public User toModel(UserMO userMO) {
        if (userMO == null) {
            return null;
        }
        return User.builder()
                .id(userMO.getId())
                .username(userMO.getUsername())
                .password(userMO.getPassword())
                .phone(userMO.getPhone())
                .info(userMO.getInfo())
                .status(EnabledEnum.fromName(userMO.getStatus()))
                .balance(userMO.getBalance())
                .createdAt(userMO.getCreatedAt())
                .updatedAt(userMO.getUpdatedAt())
                .enabled(EnabledEnum.fromName(userMO.getEnabled()))
                .build();
    }
    
    public UserMO toMO(User user) {
        if (user == null) {
            return null;
        }
        return UserMO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .phone(user.getPhone())
                .info(user.getInfo())
                .status(user.getStatus() != null ? user.getStatus().getName() : null)
                .balance(user.getBalance())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .enabled(user.getEnabled() != null ? user.getEnabled().getName() : null)
                .build();
    }
    
    public List<User> toModelList(List<UserMO> userMOList) {
        if (userMOList == null) {
            return null;
        }
        return userMOList.stream().map(this::toModel).toList();
    }
}
