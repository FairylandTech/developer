/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-05 03:18:47 UTC+08:00
 ****************************************************/
package host.fairy.facade.convert.example;

import host.fairy.application.contracts.example.UserDO;
import host.fairy.facade.contracts.example.input.UserInput;
import host.fairy.facade.contracts.example.input.UserQueryPageInput;
import host.fairy.facade.contracts.example.output.UserOutput;
import host.fairy.fairylandfuture.common.converter.BigDecimalConverter;
import host.fairy.fairylandfuture.common.converter.DateTimeConverter;
import host.fairy.fairylandfuture.enums.DateTimeFormatEnum;
import host.fairy.fairylandfuture.enums.EnabledEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Component
public class UserFacadeConverter {
    public UserDO toDO(UserInput user) {
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
                .balance(BigDecimalConverter.fromPlainString(user.getBalance()))
                .createdAt(DateTimeConverter.toLocalDateTime(user.getCreatedAt(), DateTimeFormatEnum.DATE_TIME))
                .enabled(EnabledEnum.fromDescription(user.getStatus()))
                .build();
    }
    
    public UserDO toDO(UserQueryPageInput user) {
        if (user == null) {
            return null;
        }
        
        return UserDO.builder()
                .page(user.getPage())
                .size(user.getSize())
                .username(user.getUsername())
                .phone(user.getPhone())
                .status(EnabledEnum.fromDescription(user.getStatus()))
                .startTime(user.getStartTime())
                .endTime(user.getEndTime())
                .enabled(EnabledEnum.ENABLED)
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
                .balance(BigDecimalConverter.toPlainString(user.getBalance()))
                .createdAt(DateTimeConverter.toString(user.getCreatedAt(), DateTimeFormatEnum.DATE_TIME))
                .updatedAt(DateTimeConverter.toString(user.getUpdatedAt(), DateTimeFormatEnum.DATE_TIME))
                .enabled(user.getEnabled().getDescription())
                .build();
    }
    
    public List<UserOutput> toOutputList(List<UserDO> userDOList) {
        if (userDOList == null) {
            return null;
        }
        return userDOList.stream().map(this::toOutput).toList();
    }
}
