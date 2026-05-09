/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-07 10:34:35 UTC+08:00
 ****************************************************/
package host.fairy.user.controller;

import host.fairy.fairylandfuture.common.web.response.Response;
import host.fairy.user.fiegn.ExampleFidgnClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author Beau Dean
 * @version 1.0
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final ExampleFidgnClient exampleFidgnClient;
    
    @GetMapping("")
    public Response<Object> getUser() {
        Response<String> response = exampleFidgnClient.example();
        HashMap<String, Object> hashMap = new HashMap<>() {
            {
                put("example", response.getData());
                put("user", "quickstart user service start");
            }
        };
        return Response.success(hashMap);
    }
}
