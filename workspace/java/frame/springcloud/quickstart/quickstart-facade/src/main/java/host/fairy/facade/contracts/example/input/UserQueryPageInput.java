/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-06 14:04:01 UTC+08:00
 ****************************************************/
package host.fairy.facade.contracts.example.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryPageInput {
    private Integer page = 1;
    private Integer size = 10;
    private String username;
    private String phone;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
