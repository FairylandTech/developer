/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-01 22:06:02 UTC+08:00
 ****************************************************/
package host.fairy.model.http;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Data
@Builder
public class Response<T> implements Serializable {
    
    private int code;
    private String message;
    private T data;
    
    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .code(200)
                .message("OK")
                .data(data)
                .build();
    }
}
