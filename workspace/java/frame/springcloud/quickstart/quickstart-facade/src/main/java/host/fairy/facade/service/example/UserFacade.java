/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 05:15:33 UTC+08:00
 ****************************************************/
package host.fairy.facade.service.example;

import host.fairy.facade.contracts.example.input.UserInput;
import host.fairy.facade.contracts.example.output.UserOutput;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
public interface UserFacade {
    
    UserOutput getById(Long id);
    
    UserOutput create(UserInput userInput);
    
    void update(UserInput userInput);
    
    void delete(Long id);
    
    List<UserOutput> list();
}
