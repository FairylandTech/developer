/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 03:30:47 UTC+08:00
 ****************************************************/
package host.fairy.facade.service.example.impl;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.application.service.example.UserApplicationService;
import host.fairy.facade.contracts.example.input.UserInput;
import host.fairy.facade.contracts.example.input.UserQueryPageInput;
import host.fairy.facade.contracts.example.output.UserOutput;
import host.fairy.facade.convert.example.UserFacadeConverter;
import host.fairy.facade.service.example.UserFacade;
import host.fairy.fairylandfuture.common.web.result.PageResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {
    
    private final UserFacadeConverter userFacadeConverter;
    private final UserApplicationService userApplicationService;
    
    @Override
    public UserOutput create(UserInput userInput) {
        return userFacadeConverter.toOutput(userApplicationService.createUser(userFacadeConverter.toDO(userInput)));
    }
    
    @Override
    public PageResult<UserOutput> queryList(UserQueryPageInput userQueryPageInput) {
        PageResult<UserDO> result = userApplicationService.queryUserList(userFacadeConverter.toDO(userQueryPageInput));
        return PageResult.from(result.getPage(), result.getSize(), result.getTotal(), result.getPages(), userFacadeConverter.toOutputList(result.getData()));
    }
}
