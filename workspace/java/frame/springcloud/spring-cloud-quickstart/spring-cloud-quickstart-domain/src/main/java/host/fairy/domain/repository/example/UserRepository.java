package host.fairy.domain.repository.example;

import host.fairy.domain.model.example.User;
import host.fairy.fairylandfuture.common.web.result.PageResult;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
public interface UserRepository {
    User insert(User user);
    
    User selectById(Long id);
    
    List<User> selectAll(User user);

    PageResult<User> selectPage(Integer page, Integer size, User user);
    
    User selectByUsername(String username);
    
    User delectById(Long id);
}
