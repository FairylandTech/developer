/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-04 21:31:33 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.http.controller.example;

import host.fairy.facade.contracts.example.input.UserInput;
import host.fairy.facade.contracts.example.input.UserQueryPageInput;
import host.fairy.facade.contracts.example.output.UserOutput;
import host.fairy.facade.service.example.UserFacade;
import host.fairy.fairylandfuture.common.web.response.Response;
import host.fairy.fairylandfuture.common.web.result.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Beau Dean
 * @version 1.0
 */
@RestController
@RequestMapping("/api/example/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserFacade userFacade;
    
    @GetMapping("")
    public Response<PageResult<UserOutput>> getUserPageList(UserQueryPageInput userQueryPageInput) {
        return Response.success(userFacade.queryList(userQueryPageInput));
    }
    
    @PostMapping("")
    public Response<UserOutput> createUser(@RequestBody UserInput userInput) {
        return Response.success(userFacade.create(userInput));
    }
}
