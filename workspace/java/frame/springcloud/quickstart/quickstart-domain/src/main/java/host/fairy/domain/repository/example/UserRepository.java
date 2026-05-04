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
    
    /**
     * 根据 ID 获取用户
     *
     * @param id 用户 ID
     * @return 用户
     */
    User findById(Long id);
    
    /**
     * 保存用户
     *
     * @param user 用户
     */
    void save(User user);
    
    /**
     * 删除用户
     *
     * @param id 用户 ID
     */
    void deleteById(Long id);
    
    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    List<User> findAll();
}
