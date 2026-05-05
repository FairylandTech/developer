/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 04:15:39 UTC+08:00
 ****************************************************/
package host.fairy.application.service.example.impl;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.application.convert.example.UserApplicationConverter;
import host.fairy.application.service.example.UserApplicationService;
import host.fairy.domain.model.example.User;
import host.fairy.domain.repository.example.UserRepository;
import host.fairy.domain.service.example.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserApplicationConverter userApplicationConverter;
    
    @Override
    public UserDO createUser(UserDO user) {
        log.info("Step 2: Application UserDO -> {}", user.toString());
        User validatedUser = userService.createUser(userApplicationConverter.toModel(user));
        userRepository.save(validatedUser);
        User userQueryResult = userRepository.findByUsername(validatedUser.getUsername());
        log.info("Step 3: Application userQueryResult -> {}", userQueryResult.toString());
        UserDO userDO = userApplicationConverter.toDO(userQueryResult);
        log.info("Step 4: Application userDO -> {}", userDO.toString());
        return userDO;
    }
    
    @Override
    public UserDO queryUserById(Long Id) {
        return null;
    }
    
    @Override
    public List<UserDO> queryUserList(Integer page, Integer size, UserDO user) {
        return List.of();
    }
    
    @Override
    public UserDO updateUser(UserDO user) {
        return null;
    }
    
    @Override
    public void deleteUserById(Long Id) {
    
    }
}
