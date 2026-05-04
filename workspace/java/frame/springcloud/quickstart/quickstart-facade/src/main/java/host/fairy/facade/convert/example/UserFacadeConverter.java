/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 03:18:47 UTC+08:00
 ****************************************************/
package host.fairy.facade.convert.example;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.facade.contracts.example.output.UserOutput;
import org.springframework.stereotype.Component;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Component
public class UserFacadeConverter {
    public UserDO toDO(UserOutput user) {
        if (user == null) {
            return null;
        }
        
        UserDO dto = new UserDO();
        dto.setId(user.getId());
        return dto;
    }
    
    public UserOutput toOutput(UserDO user) {
        if (user == null) {
            return null;
        }
        
        UserOutput dto = new UserOutput();
        dto.setId(user.getId());
        return dto;
    }
}
