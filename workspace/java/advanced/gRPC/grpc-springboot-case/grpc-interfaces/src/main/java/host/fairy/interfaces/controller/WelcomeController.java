/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-21 03:33:29 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.controller;

import host.fairy.application.service.WelcomeService;
import host.fairy.fairylandfuture.common.web.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/welcome")
public class WelcomeController {
    
    @Autowired
    private WelcomeService welcomeService;
    
    @GetMapping("/{name}")
    public Response<String> welcome(@PathVariable String name) {
        log.info("Params: {}", name);
        return Response.success(this.welcomeService.welcome(name));
    }
    
    @PostMapping("")
    public Response<String> postWelcome(@RequestBody Map<String, String> params) {
        params.forEach((key, value) -> log.info("{} -> {}", key, value));
        return Response.success();
    }
}
