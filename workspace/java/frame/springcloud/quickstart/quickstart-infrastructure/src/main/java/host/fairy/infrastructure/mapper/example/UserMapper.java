/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Junie (AI Assistant)
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 00:33 UTC+08:00
 ****************************************************/
package host.fairy.infrastructure.mapper.example;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import host.fairy.infrastructure.model.example.UserMO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper 接口
 *
 * @author Beau Dean
 * @version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<UserMO> {
}
