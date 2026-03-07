/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-02 20:14:41 UTC+08:00
 ****************************************************/
package host.fairy.mapper;

import host.fairy.model.SimpleUserModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Mapper
public interface SimpleUserMapper {
    List<SimpleUserModel> selectSimpleUsers();
}
