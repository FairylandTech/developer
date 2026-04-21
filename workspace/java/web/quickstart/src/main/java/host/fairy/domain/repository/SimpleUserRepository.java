/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-07 UTC+08:00
 ****************************************************/
package host.fairy.domain.repository;

import host.fairy.domain.model.SimpleUserModel;

import java.util.List;

/**
 * SimpleUser 仓储接口
 * 在domain层定义，在infrastructure层实现
 * 
 * @author Beau Dean
 * @version 1.0
 */
public interface SimpleUserRepository {
    
    /**
     * 查询所有用户
     */
    List<SimpleUserModel> findAll();
    
    /**
     * 根据ID查询用户
     */
    SimpleUserModel findById(Long id);
    
    /**
     * 保存或更新用户
     */
    SimpleUserModel save(SimpleUserModel user);
    
    /**
     * 删除用户
     */
    void delete(Long id);
    
    /**
     * 根据用户名查询
     */
    SimpleUserModel findByUsername(String username);
}
