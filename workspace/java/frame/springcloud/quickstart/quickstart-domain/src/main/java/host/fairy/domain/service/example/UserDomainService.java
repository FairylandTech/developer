package host.fairy.domain.service.example;

import host.fairy.domain.model.example.User;

import java.util.List;

/**
 * User 领域服务接口
 * 处理涉及多个聚合根或不属于单一实体的核心业务逻辑
 *
 * @author Junie
 * @version 1.0
 */
public interface UserDomainService {
    
    /**
     * 注册用户（包含业务校验）
     */
    void register(User user);
    
    /**
     * 更新用户信息
     */
    void update(User user);
    
    /**
     * 禁用/启用用户
     */
    void changeStatus(Long userId, String status);
    
    /**
     * 检查用户是否存在
     */
    boolean exists(Long userId);
    
    /**
     * 获取用户实体
     */
    User getById(Long id);
    
    /**
     * 删除用户
     */
    void delete(Long id);
    
    /**
     * 获取所有用户实体
     */
    List<User> list();
}
