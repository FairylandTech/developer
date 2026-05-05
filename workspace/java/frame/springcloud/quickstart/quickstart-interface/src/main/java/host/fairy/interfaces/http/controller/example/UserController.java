/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-04 21:31:33 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.http.controller.example;

import host.fairy.facade.contracts.example.input.UserCreateInput;
import host.fairy.facade.contracts.example.output.UserOutput;
import host.fairy.facade.service.example.UserFacade;
import host.fairy.fairylandfuture.common.web.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
@RestController
@RequestMapping("/example/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserFacade userFacade;
    
    @GetMapping("/{id}")
    public Response<UserOutput> getById(@PathVariable Long id) {
        return Response.success(userFacade.getById(id));
    }
    
    @PostMapping("")
    public Response<UserOutput> create(@RequestBody UserCreateInput command) {
        return Response.success(userFacade.create(command));
    }
    
    @PutMapping("/{id}")
    public Response<Void> update(@PathVariable Long id, @RequestBody UserCreateInput command) {
        command.setId(id);
        userFacade.update(command);
        return Response.success();
    }
    
    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        userFacade.delete(id);
        return Response.success();
    }
    
    @GetMapping("")
    public Response<List<UserOutput>> list() {
        return Response.success(userFacade.list());
    }
}
