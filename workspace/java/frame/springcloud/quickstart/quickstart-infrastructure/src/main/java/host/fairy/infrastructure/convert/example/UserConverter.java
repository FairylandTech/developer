package host.fairy.infrastructure.convert.example;

import host.fairy.domain.model.example.User;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import host.fairy.infrastructure.model.example.UserMO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User 转换器
 *
 * @author Beau Dean
 * @version 1.0
 */
@Component
public class UserConverter {
    
    /**
     * 将 MO 转换为领域实体
     *
     * @param mo 数据对象
     * @return 领域实体
     */
    public User toEntity(UserMO mo) {
        if (mo == null) {
            return null;
        }
        return User.builder()
                .id(mo.getId())
                .username(mo.getUsername())
                .password(mo.getPassword())
                .phone(mo.getPhone())
                .info(mo.getInfo())
                .status(EnabledEnum.fromName(mo.getStatus()))
                .balance(mo.getBalance())
                .createdAt(mo.getCreatedAt())
                .updatedAt(mo.getUpdatedAt())
                .enabled(EnabledEnum.fromName(mo.getEnabled()))
                .build();
    }
    
    /**
     * 将领域实体转换为 MO
     *
     * @param user 领域实体
     * @return 数据对象
     */
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
    
    public List<User> toEntityList(List<UserMO> mos) {
        if (mos == null) {
            return null;
        }
        return mos.stream().map(this::toEntity).toList();
    }
}
