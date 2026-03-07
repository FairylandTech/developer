/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-02 06:26:38 UTC+08:00
 ****************************************************/
package host.fairy.model;

import host.fairy.fairylandfuture.model.ModelBase;
import lombok.*;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SimpleUserModel extends ModelBase {
    private String username;
    private String password;
    private String name;
    private Integer age;
}
