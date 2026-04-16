/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-07 UTC+08:00
 ****************************************************/
package host.fairy.infrastructure.persistence.repository;

import host.fairy.domain.model.SimpleUserModel;
import host.fairy.domain.repository.SimpleUserRepository;
import host.fairy.infrastructure.repository.mapper.SimpleUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SimpleUser 仓储实现
 * 在infrastructure层实现，调用MyBatis Mapper进行数据库操作
 * 
 * @author Lionel Johnson
 * @version 1.0
 */
@Repository
public class SimpleUserRepositoryImpl implements SimpleUserRepository {
    
    private final SimpleUserMapper mapper;
    
    @Autowired
    public SimpleUserRepositoryImpl(SimpleUserMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public List<SimpleUserModel> findAll() {
        return this.mapper.selectSimpleUsers();
    }
    
    @Override
    public SimpleUserModel findById(Long id) {
        return this.mapper.selectById(id);
    }
    
    @Override
    public SimpleUserModel save(SimpleUserModel user) {
        if (user.getId() == null) {
            this.mapper.insert(user);
        } else {
            this.mapper.update(user);
        }
        return user;
    }
    
    @Override
    public void delete(Long id) {
        this.mapper.deleteById(id);
    }
    
    @Override
    public SimpleUserModel findByUsername(String username) {
        return this.mapper.selectByUsername(username);
    }
}
