package host.fairy.facade.service.example;

import host.fairy.facade.contracts.example.input.UserCreateInput;
import host.fairy.facade.contracts.example.output.UserOutput;

import java.util.List;

/**
 * User Service 接口
 *
 * @author Junie
 * @version 1.0
 */
public interface UserService {
    
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
    void create(UserCreateInput userCreateInput);
    
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
