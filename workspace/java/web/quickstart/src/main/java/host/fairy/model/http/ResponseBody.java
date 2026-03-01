/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-01 22:06:02 UTC+08:00
 ****************************************************/
package host.fairy.model.http;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class ResponseBody<T> implements Serializable {
    
    private int code;
    private String message;
    private T data;
    
    public static <T> ResponseBody<T> success(T data) {
        ResponseBody<T> response = new ResponseBody<>();
        response.code = 200;
        response.message = "OK";
        response.data = data;
        return response;
    }
}
