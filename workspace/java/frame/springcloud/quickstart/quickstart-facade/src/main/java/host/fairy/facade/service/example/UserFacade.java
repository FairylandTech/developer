/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 05:15:33 UTC+08:00
 ****************************************************/
package host.fairy.facade.service.example;

import host.fairy.facade.contracts.example.input.UserCreateInput;
import host.fairy.facade.contracts.example.output.UserOutput;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
public interface UserFacade {
    
    /**
     * 根据 ID 获取用户
     *
     * @param id 用户 ID
     * @return 用户 Output
     */
    UserOutput getById(Long id);
    
    /**
     * 创建用户
     *
     * @param userCreateInput 用户创建输入 Input
     */
    UserOutput create(UserCreateInput userCreateInput);
    
    /**
     * 更新用户
     *
     * @param userCreateInput 用户更新输入 Input
     */
    void update(UserCreateInput userCreateInput);
    
    /**
     * 删除用户
     *
     * @param id 用户 ID
     */
    void delete(Long id);
    
    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    List<UserOutput> list();
}
