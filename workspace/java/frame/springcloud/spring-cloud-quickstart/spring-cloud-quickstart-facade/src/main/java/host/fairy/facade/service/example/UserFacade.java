/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 05:15:33 UTC+08:00
 ****************************************************/
package host.fairy.facade.service.example;

import host.fairy.facade.contracts.example.input.UserInput;
import host.fairy.facade.contracts.example.input.UserQueryPageInput;
import host.fairy.facade.contracts.example.output.UserOutput;
import host.fairy.fairylandfuture.common.web.result.PageResult;

/**
 * @author Beau Dean
 * @version 1.0
 */
public interface UserFacade {
    UserOutput create(UserInput userInput);
    
    PageResult<UserOutput> queryList(UserQueryPageInput userQueryPageInput);
}
