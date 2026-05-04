package host.fairy.application.service.example;

import host.fairy.domain.model.example.User;
import host.fairy.domain.repository.example.UserRepository;
import host.fairy.domain.service.example.UserDomainService;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import host.fairy.fairylandfuture.exception.business.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserDomainService 实现类
 *
 * @author Junie
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDomainService {
    
    private final UserRepository userRepository;
    
    @Override
    public void register(User user) {
        // 领域逻辑：例如检查用户名是否已存在（虽然目前 repository 还没写这个方法，这里作为演示）
        userRepository.save(user);
    }
    
    @Override
    public void update(User user) {
        if (!exists(user.getId())) {
            throw new BusinessException("用户不存在");
        }
        userRepository.save(user);
    }
    
    @Override
    public void changeStatus(Long userId, String status) {
        User user = getById(userId);
        user.changeStatus(EnabledEnum.fromName(status));
        userRepository.save(user);
    }
    
    @Override
    public boolean exists(Long userId) {
        return userRepository.findById(userId) != null;
    }
    
    @Override
    public User getById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }
    
    @Override
    public List<User> list() {
        return userRepository.findAll();
    }
    
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
