package host.fairy.infrastructure.persistence.repository.example;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import host.fairy.domain.model.example.User;
import host.fairy.domain.repository.example.UserRepository;
import host.fairy.fairylandfuture.common.web.result.PageResult;
import host.fairy.infrastructure.persistence.converter.example.UserConverter;
import host.fairy.infrastructure.persistence.mapper.example.UserMapper;
import host.fairy.infrastructure.persistence.model.example.UserMO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    private final UserMapper userMapper;
    private final UserConverter userConverter;
    
    @Override
    public User insert(User user) {
        UserMO userMO = userConverter.toMO(user);
        if (userMO.getId() == null) {
            userMapper.insert(userMO);
        } else {
            userMapper.updateById(userMO);
        }
        UserMO userMO1 = userMapper.selectByUsername(userMO.getUsername());
        return userConverter.toModel(userMO1);
    }
    
    @Override
    public User selectById(Long id) {
        return userConverter.toModel(userMapper.selectById(id));
    }
    
    @Override
    public List<User> selectAll(User user) {
        return List.of();
    }
    
    @Override
    public PageResult<User> selectPage(Integer page, Integer size, User user) {
        Page<UserMO> pagination = new Page<>(page, size);
        
        LambdaQueryWrapper<UserMO> wrapper = new LambdaQueryWrapper<UserMO>()
                .like(StringUtils.isNotBlank(user.getUsername()), UserMO::getUsername, user.getUsername())
                .like(ObjectUtils.isNotEmpty(user.getPhone()), UserMO::getPhone, user.getPhone())
                .eq(ObjectUtils.isNotEmpty(user.getStatus()), UserMO::getStatus, user.getStatus())
                .gt(ObjectUtils.isNotEmpty(user.getCreatedAt()), UserMO::getCreatedAt, user.getCreatedAt())
                .lt(ObjectUtils.isNotEmpty(user.getUpdatedAt()), UserMO::getUpdatedAt, user.getUpdatedAt());
        Page<UserMO> queryResult = userMapper.selectPage(pagination, wrapper);
        
        long currentPageNumber = queryResult.getCurrent();
        long currentPageSize = queryResult.getSize();
        long total = queryResult.getTotal();
        long pages = queryResult.getPages();
        List<UserMO> records = queryResult.getRecords();
        log.info("分页查询用户列表, 当前页: {}, 每页条数: {}, 总记录数: {}, 总页数: {}, 当前页数据: {}", currentPageNumber, currentPageSize, total, pages, records);
        return PageResult.from(currentPageNumber, currentPageSize, total, pages, userConverter.toModelList(records));
    }
    
    @Override
    public User selectByUsername(String username) {
        return userConverter.toModel(userMapper.selectByUsername(username));
    }
    
    @Override
    public User delectById(Long id) {
        return null;
    }
}
