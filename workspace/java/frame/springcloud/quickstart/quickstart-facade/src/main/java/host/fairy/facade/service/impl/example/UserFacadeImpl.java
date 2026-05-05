/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 03:30:47 UTC+08:00
 ****************************************************/
package host.fairy.facade.service.impl.example;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.application.service.example.UserApplicationService;
import host.fairy.facade.contracts.example.input.UserCreateInput;
import host.fairy.facade.contracts.example.output.UserOutput;
import host.fairy.facade.convert.example.UserFacadeConverter;
import host.fairy.facade.service.example.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {
    
    private final UserFacadeConverter userFacadeConverter;
    private final UserApplicationService userApplicationService;
    
    @Override
    public UserOutput getById(Long id) {
        return userFacadeConverter.toOutput(new UserDO());
    }
    
    @Override
    public UserOutput create(UserCreateInput userCreateInput) {
        System.out.println("Step 1: Facade UserCreateInput -> " + userCreateInput.toString());
        UserDO userDO = userFacadeConverter.toDO(userCreateInput);
        UserDO result = userApplicationService.createUser(userDO);
        UserOutput output = userFacadeConverter.toOutput(result);
        System.out.println("Result 2: Facade UserOutput -> " + output.toString());
        return output;
    }
    
    @Override
    public void update(UserCreateInput userCreateInput) {
        UserDO userDO = userFacadeConverter.toDO(userCreateInput);
    }
    
    @Override
    public void delete(Long id) {
    }
    
    @Override
    public List<UserOutput> list() {
        return List.of();
    }
}
