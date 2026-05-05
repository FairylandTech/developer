/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 03:18:47 UTC+08:00
 ****************************************************/
package host.fairy.facade.convert.example;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.facade.contracts.example.input.UserCreateInput;
import host.fairy.facade.contracts.example.output.UserOutput;
import host.fairy.fairylandfuture.enums.DateTimeFormatEnum;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import host.fairy.fairylandfuture.utils.converter.BigDecimalConverterUtils;
import host.fairy.fairylandfuture.utils.converter.DateTimeConverterUtils;
import org.springframework.stereotype.Component;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Component
public class UserFacadeConverter {
    public UserDO toDO(UserCreateInput user) {
        if (user == null) {
            return null;
        }
        
        return UserDO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .phone(user.getPhone())
                .info(user.getInfo())
                .status(EnabledEnum.fromDescription(user.getStatus()))
                .balance(BigDecimalConverterUtils.fromPlainString(user.getBalance()))
                .createdAt(DateTimeConverterUtils.toLocalDateTime(user.getCreatedAt(), DateTimeFormatEnum.DATE_TIME))
                .enabled(EnabledEnum.fromDescription(user.getStatus()))
                .build();
    }
    
    public UserOutput toOutput(UserDO user) {
        if (user == null) {
            return null;
        }
        return UserOutput.builder()
                .id(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .info(user.getInfo())
                .status(user.getStatus().getDescription())
                .balance(BigDecimalConverterUtils.toPlainString(user.getBalance()))
                .createdAt(DateTimeConverterUtils.toString(user.getCreatedAt(), DateTimeFormatEnum.DATE_TIME))
                .updatedAt(DateTimeConverterUtils.toString(user.getUpdatedAt(), DateTimeFormatEnum.DATE_TIME))
                .enabled(user.getEnabled().getDescription())
                .build();
    }
}
