/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 04:15:39 UTC+08:00
 ****************************************************/
package host.fairy.application.service.example.impl;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.application.converter.example.UserApplicationConverter;
import host.fairy.application.service.example.UserApplicationService;
import host.fairy.domain.model.example.User;
import host.fairy.domain.repository.example.UserRepository;
import host.fairy.domain.service.example.UserDomainService;
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
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final UserApplicationConverter userApplicationConverter;
    
    @Override
    public UserDO createUser(UserDO userDO) {
        User user = userDomainService.createUser(userApplicationConverter.toModel(userDO));
        User insertUser = userRepository.insert(user);
        return userApplicationConverter.toDO(insertUser);
    }
    
    @Override
    public PageResult<UserDO> queryUserList(UserDO userDO) {
        PageResult<User> result = userRepository.selectPage(userDO.getPage(), userDO.getSize(), userApplicationConverter.toModel(userDO));
        return PageResult.from(result.getPage(), result.getSize(), result.getTotal(), result.getPages(), userApplicationConverter.toDOList(result.getData()));
    }
}
