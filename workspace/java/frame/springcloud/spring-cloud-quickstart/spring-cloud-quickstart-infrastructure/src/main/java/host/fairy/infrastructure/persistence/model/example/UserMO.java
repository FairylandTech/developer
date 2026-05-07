/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-04 20:30:50 UTC+08:00
 ****************************************************/
package host.fairy.infrastructure.persistence.model.example;

import com.baomidou.mybatisplus.annotation.TableName;
import host.fairy.fairylandfuture.domain.model.MOBase;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class UserMO extends MOBase {
    private String username;
    
    private String password;
    
    private String phone;
    
    private String info;
    
    private String status;
    
    private BigDecimal balance;
}
