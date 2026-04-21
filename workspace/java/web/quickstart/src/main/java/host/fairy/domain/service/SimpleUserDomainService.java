/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-07 UTC+08:00
 ****************************************************/
package host.fairy.domain.service;

import host.fairy.domain.model.SimpleUserModel;
import host.fairy.domain.repository.SimpleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SimpleUser 领域服务
 * 处理跨越多个聚合根的业务逻辑或需要仓储支持的业务规则
 * 
 * @author Beau Dean
 * @version 1.0
 */
@Service
public class SimpleUserDomainService {
    
    private final SimpleUserRepository repository;
    
    @Autowired
    public SimpleUserDomainService(SimpleUserRepository repository) {
        this.repository = repository;
    }
    
    /**
     * 验证用户名是否唯一
     */
    public boolean isUsernameUnique(String username) {
        SimpleUserModel user = repository.findByUsername(username);
        return user == null;
    }
    
    /**
     * 验证用户名唯一性，如果不唯一则抛出异常
     */
    public void validateUsernameUnique(String username) {
        if (!isUsernameUnique(username)) {
            throw new IllegalArgumentException("用户名已存在: " + username);
        }
    }
    
    /**
     * 验证密码强度
     */
    public boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 6;
    }
}
