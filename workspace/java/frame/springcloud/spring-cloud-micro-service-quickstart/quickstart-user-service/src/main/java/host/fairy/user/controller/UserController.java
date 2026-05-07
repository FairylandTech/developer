/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-07 10:34:35 UTC+08:00
 ****************************************************/
package host.fairy.user.controller;

import host.fairy.fairylandfuture.common.web.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Beau Dean
 * @version 1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @GetMapping("")
    public Response<String> getUser() {
        return Response.success("quickstart user service start");
    }
}
