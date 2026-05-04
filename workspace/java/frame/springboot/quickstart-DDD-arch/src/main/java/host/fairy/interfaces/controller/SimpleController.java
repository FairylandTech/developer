/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-01 21:46:25 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.controller;

import host.fairy.fairylandfuture.common.web.response.Response;
import host.fairy.domain.model.RequestInfo;
import host.fairy.domain.model.SimpleUserModel;
import host.fairy.application.service.SimpleApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SimpleUser HTTP接口
 * 表示层：处理HTTP请求和响应
 * 调用应用层服务进行业务处理
 * 
 * @author Beau Dean
 * @version 1.0
 */
@RestController
@RequestMapping("/simple")
public class SimpleController {
    
    private final SimpleApplicationService applicationService;
    
    @Autowired
    public SimpleController(SimpleApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    
    /**
     * 欢迎接口
     */
    @GetMapping("/welcome")
    public Response<String> welcome() {
        return Response.success("Welcome to Spring Boot quickstart.");
    }
    
    /**
     * 获取HTTP请求信息
     */
    @GetMapping("/request")
    public Response<RequestInfo> request(HttpServletRequest request) {
        return Response.success(this.applicationService.getRequestInfo(request));
    }
    
    /**
     * 响应测试接口
     */
    @GetMapping("/response")
    public Response<String> response() {
        return Response.success("This is a simple response.");
    }
    
    /**
     * 获取所有用户（从文件）
     */
    @GetMapping("/user")
    public Response<List<SimpleUserModel>> user() {
        return Response.success(this.applicationService.getSimpleUsers());
    }
    
    /**
     * 获取所有用户（从数据库）
     */
    @GetMapping("/duser")
    public Response<List<SimpleUserModel>> duser() {
        return Response.success(this.applicationService.getSimpleUsersFromDatabase());
    }
    
    /**
     * 根据ID获取用户
     */
    @GetMapping("/user/{id}")
    public Response<SimpleUserModel> getUserById(@PathVariable Long id) {
        SimpleUserModel user = this.applicationService.getUserById(id);
        if (user == null) {
            return Response.success(null);  // 用户不存在返回null
        }
        return Response.success(user);
    }
    
    /**
     * 创建新用户
     */
    @PostMapping("/user")
    public Response<SimpleUserModel> createUser(@RequestBody SimpleUserModel user) {
        try {
            SimpleUserModel createdUser = this.applicationService.createUser(user);
            return Response.success(createdUser);
        } catch (IllegalArgumentException e) {
            return Response.success();  // 异常情况返回null
        }
    }
    
    /*
    * 创建用户2
    * */
    @PostMapping("/user2")
    public Response<?> createUser2(@RequestBody SimpleUserModel user) {
        try {
            this.applicationService.createUser(user);
            return Response.success();
        } catch (Exception exception) {
            return Response.success();
        }
    }
}
