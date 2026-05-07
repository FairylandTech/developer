/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-06 05:33:08 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.http.advice;

import host.fairy.fairylandfuture.common.web.response.Response;
import host.fairy.fairylandfuture.exception.business.BusinessExceptionBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@Order(0)
@RestControllerAdvice
public class BusinessExceptionAdvice {
    @ExceptionHandler(BusinessExceptionBase.class)
    public Response<?> handleBusinessException(BusinessExceptionBase exception) {
        log.error("BusinessExceptionAdvice:BusinessExceptionBase: {}", exception.getMessage(), exception);
        return Response.failure(exception.getMessage());
    }
}
