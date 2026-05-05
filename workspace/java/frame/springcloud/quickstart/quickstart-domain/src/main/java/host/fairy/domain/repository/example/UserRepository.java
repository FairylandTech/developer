package host.fairy.domain.repository.example;

import host.fairy.domain.model.example.User;

import java.util.List;

/**
 * User 仓库接口
 *
 * @author Beau Dean
 * @version 1.0
 */
public interface UserRepository {
    void save(User user);
    
    User findById(Long id);
    
    List<User> findAll();
    
    User findByUsername(String username);
    
    void deleteById(Long id);
}
