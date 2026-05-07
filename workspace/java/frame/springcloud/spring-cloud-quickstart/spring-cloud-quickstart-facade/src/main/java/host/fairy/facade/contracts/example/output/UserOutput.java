/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 05:18:07 UTC+08:00
 ****************************************************/
package host.fairy.facade.contracts.example.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutput implements Serializable {
    private Long id;
    private String username;
    private String phone;
    private String info;
    private String status;
    private String balance;
    private String createdAt;
    private String updatedAt;
    private String enabled;
}
