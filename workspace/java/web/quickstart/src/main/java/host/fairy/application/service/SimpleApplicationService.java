/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-07 UTC+08:00
 ****************************************************/
package host.fairy.application.service;

import host.fairy.domain.model.RequestInfo;
import host.fairy.domain.model.SimpleUserModel;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * SimpleUser 应用服务接口
 * 负责业务流程的编排、事务管理、调用domain层和infrastructure层
 * 
 * @author Lionel Johnson
 * @version 1.0
 */
public interface SimpleApplicationService {
    
    /**
     * 获取HTTP请求信息
     */
    RequestInfo getRequestInfo(HttpServletRequest request);
    
    /**
     * 从文件获取所有用户（演示用）
     */
    List<SimpleUserModel> getSimpleUsers();
    
    /**
     * 从数据库获取所有用户
     */
    List<SimpleUserModel> getSimpleUsersFromDatabase();
    
    /**
     * 根据ID获取用户
     */
    SimpleUserModel getUserById(Long id);
    
    /**
     * 创建用户
     */
    SimpleUserModel createUser(SimpleUserModel user);
}
