package host.fairy.facade.contracts.example.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * User 创建/更新 Command
 *
 * @author Junie
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateInput implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String info;
    private String status;
    private Long balance;
    private String enabled;
}
