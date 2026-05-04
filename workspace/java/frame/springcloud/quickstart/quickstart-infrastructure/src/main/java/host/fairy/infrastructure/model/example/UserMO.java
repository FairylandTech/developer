/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-04 20:30:50 UTC+08:00
 ****************************************************/
package host.fairy.infrastructure.model.example;

import com.baomidou.mybatisplus.annotation.TableName;
import host.fairy.fairylandfuture.domain.model.MOBase;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_user")
public class UserMO extends MOBase {
    private String username;
    private String password;
    private String phone;
    private String info;
    private EnabledEnum status;
    private Long balance;
}
