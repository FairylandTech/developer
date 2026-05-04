/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 03:03:28 UTC+08:00
 ****************************************************/
package host.fairy.application.contracts.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDO implements Serializable {
    private Long id;
    
    private String name;
    
    private String phone;
}
