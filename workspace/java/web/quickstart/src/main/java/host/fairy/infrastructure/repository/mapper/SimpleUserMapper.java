/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-02 20:14:41 UTC+08:00
 ****************************************************/
package host.fairy.infrastructure.repository.mapper;

import host.fairy.domain.model.SimpleUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Mapper
public interface SimpleUserMapper {
    
    List<SimpleUserModel> selectSimpleUsers();
    
    SimpleUserModel selectById(@Param("id") Long id);
    
    SimpleUserModel selectByUsername(@Param("username") String username);
    
    int insert(SimpleUserModel user);
    
    int update(SimpleUserModel user);
    
    int deleteById(@Param("id") Long id);
}
