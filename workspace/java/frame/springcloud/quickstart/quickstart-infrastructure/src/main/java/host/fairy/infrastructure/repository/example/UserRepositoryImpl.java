package host.fairy.infrastructure.repository.example;

import host.fairy.domain.model.example.User;
import host.fairy.domain.repository.example.UserRepository;
import host.fairy.infrastructure.convert.example.UserConverter;
import host.fairy.infrastructure.mapper.example.UserMapper;
import host.fairy.infrastructure.model.example.UserMO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository 实现类
 *
 * @author Beau Dean
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    private final UserMapper userMapper;
    private final UserConverter userConverter;
    
    @Override
    public User findById(Long id) {
        UserMO userMO = userMapper.selectById(id);
        return userConverter.toEntity(userMO);
    }
    
    @Override
    public void save(User user) {
        UserMO userMO = userConverter.toMO(user);
        if (userMO.getId() == null) {
            userMapper.insert(userMO);
            user.setId(userMO.getId());
        } else {
            userMapper.updateById(userMO);
        }
    }
    
    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        List<UserMO> mos = userMapper.selectList(null);
        return userConverter.toEntityList(mos);
    }
}
