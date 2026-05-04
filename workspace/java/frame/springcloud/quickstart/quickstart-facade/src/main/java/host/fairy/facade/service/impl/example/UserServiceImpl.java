/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 03:30:47 UTC+08:00
 ****************************************************/
package host.fairy.facade.service.impl.example;

import host.fairy.facade.contracts.example.input.UserCreateInput;
import host.fairy.facade.contracts.example.output.UserOutput;
import host.fairy.facade.service.example.UserService;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    @Override
    public UserOutput getById(Long id) {
        return null;
    }
    
    @Override
    public void create(UserCreateInput userCreateInput) {
    
    }
    
    @Override
    public void update(UserCreateInput userCreateInput) {
    
    }
    
    @Override
    public void delete(Long id) {
    
    }
    
    @Override
    public List<UserOutput> list() {
        return List.of();
    }
}
