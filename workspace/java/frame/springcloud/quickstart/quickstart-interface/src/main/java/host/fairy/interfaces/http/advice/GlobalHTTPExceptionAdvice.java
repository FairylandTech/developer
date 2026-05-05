/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-06 05:29:36 UTC+08:00
 ****************************************************/
package host.fairy.interfaces.http.advice;

import host.fairy.fairylandfuture.common.web.response.Response;
import host.fairy.fairylandfuture.exception.ExceptionBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Slf4j
@Order(99)
@RestControllerAdvice
public class GlobalHTTPExceptionAdvice {
    
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception exception) {
        log.error("Exception: {}", exception.getMessage(), exception);
        return Response.failure("未知错误");
    }
    
    @ExceptionHandler(ExceptionBase.class)
    public Response<?> handleExceptionBase(ExceptionBase exception) {
        log.error("ExceptionBase: {}", exception.getMessage(), exception);
        return Response.failure(exception.getMessage());
    }
}
