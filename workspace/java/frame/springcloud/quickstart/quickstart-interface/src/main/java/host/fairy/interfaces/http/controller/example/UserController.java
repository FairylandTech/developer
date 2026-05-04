/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-04 21:31:33 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.http.controller.example;

import host.fairy.fairylandfuture.common.web.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Beau Dean
 * @version 1.0
 */
@RestController
@RequestMapping("/example/user")
public class UserController {
    
    @GetMapping("")
    public Response<?> list() {
        return Response.success();
    }
}
