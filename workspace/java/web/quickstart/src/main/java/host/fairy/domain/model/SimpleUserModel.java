/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-02 06:26:38 UTC+08:00
 ****************************************************/
package host.fairy.domain.model;

import host.fairy.fairylandfuture.model.ModelBase;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * SimpleUser 聚合根 - 充血模型
 * 包含用户的属性和业务逻辑方法
 * 
 * @author Lionel Johnson
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SimpleUserModel extends ModelBase {
    private String username;
    private String password;
    private String name;
    private Integer age;
    
    /**
     * 更改密码
     */
    public void changePassword(String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("密码长度不能少于6位");
        }
        this.password = newPassword;
    }
    
    /**
     * 验证密码是否匹配
     */
    public boolean matchesPassword(String rawPassword) {
        return this.password != null && this.password.equals(rawPassword);
    }
    
    /**
     * 检查用户是否活跃（未被删除）
     */
    public boolean isActive() {
        return true; // 默认返回true，可根据实际情况扩展
    }
    
    /**
     * 获取用户显示名称
     */
    public String getDisplayName() {
        return this.name != null ? this.name : this.username;
    }
}
