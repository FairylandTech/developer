/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-04-21 03:33:29 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.controller;

import host.fairy.application.service.WelcomeService;
import host.fairy.fairylandfuture.common.web.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lionel Johnson
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
}
