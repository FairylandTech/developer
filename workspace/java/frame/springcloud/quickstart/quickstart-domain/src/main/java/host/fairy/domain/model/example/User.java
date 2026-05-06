/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 01:23:18 UTC+08:00
 ****************************************************/
package host.fairy.domain.model.example;

import host.fairy.fairylandfuture.domain.model.ModelBase;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class User extends ModelBase {
    private String username;
    private String password;
    private String phone;
    private String info;
    private EnabledEnum status;
    private BigDecimal balance;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
