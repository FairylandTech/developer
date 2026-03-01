/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-02 06:26:38 UTC+08:00
 ****************************************************/
package host.fairy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Data
@Builder
public class SimpleUser {
    private Long id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private LocalDateTime createdAt;
}
