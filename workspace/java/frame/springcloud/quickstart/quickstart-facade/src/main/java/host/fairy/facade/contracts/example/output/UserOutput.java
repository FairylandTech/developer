package host.fairy.facade.contracts.example.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User DTO
 *
 * @author Junie
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
    private Long balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String enabled;
}
