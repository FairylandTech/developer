/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 01:23:18 UTC+08:00
 ****************************************************/
package host.fairy.domain.model.example;

import host.fairy.fairylandfuture.domain.model.ModelBase;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import host.fairy.fairylandfuture.exception.business.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends ModelBase {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String info;
    private EnabledEnum status;
    private Long balance;
    private EnabledEnum enabled;
    
    /**
     * 更新基本信息
     */
    public void updateInfo(String phone, String info) {
        this.phone = phone;
        this.info = info;
    }
    
    /**
     * 充值
     */
    public void deposit(Long amount) {
        if (amount == null || amount <= 0) {
            throw new BusinessException("充值金额必须大于0");
        }
        this.balance = (this.balance == null ? 0 : this.balance) + amount;
    }
    
    /**
     * 扣款
     */
    public void withdraw(Long amount) {
        if (amount == null || amount <= 0) {
            throw new BusinessException("扣款金额必须大于0");
        }
        if (this.balance == null || this.balance < amount) {
            throw new BusinessException("余额不足");
        }
        this.balance -= amount;
    }
    
    /**
     * 启用/禁用
     */
    public void changeStatus(EnabledEnum status) {
        this.status = status;
    }
}
