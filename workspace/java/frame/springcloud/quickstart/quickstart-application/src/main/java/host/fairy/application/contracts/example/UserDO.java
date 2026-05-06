/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 03:03:28 UTC+08:00
 ****************************************************/
package host.fairy.application.contracts.example;

import host.fairy.domain.model.example.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
public class UserDO extends User {
    private Integer page;
    private Integer size;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
