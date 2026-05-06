/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 05:17:06 UTC+08:00
 ****************************************************/
package host.fairy.application.service.example;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.fairylandfuture.common.web.result.PageResult;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
public interface UserApplicationService {
    UserDO createUser(UserDO user);
    
    PageResult<UserDO> queryUserList(UserDO userDO);
}
