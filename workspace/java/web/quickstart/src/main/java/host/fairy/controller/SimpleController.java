/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-01 21:46:25 UTC+08:00
 ****************************************************/
package host.fairy.controller;

import host.fairy.model.RequestInfo;
import host.fairy.model.SimpleUser;
import host.fairy.model.http.Response;
import host.fairy.service.SimpleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@RestController
@RequestMapping("/simple")
public class SimpleController {
    
    private final SimpleService simpleService;
    
    @Autowired
    public SimpleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }
    
    @GetMapping("/welcome")
    public Response<String> welcome() {
        return Response.success("Welcome to Spring Boot quickstart.");
    }
    
    @GetMapping("/request")
    public Response<RequestInfo> request(HttpServletRequest request) {
        return Response.success(this.simpleService.getRequestInfo(request));
    }
    
    @GetMapping("/response")
    public Response<String> response() {
        return Response.success("This is a simple response.");
    }
    
    @GetMapping("/user")
    public Response<List<SimpleUser>> user() {
        return Response.success(this.simpleService.getSimpleUsers());
    }
}
