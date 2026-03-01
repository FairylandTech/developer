/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-02 05:11:30 UTC+08:00
 ****************************************************/
package host.fairy.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Data
@Builder
public class RequestInfo {
    private String method;
    private String uri;
    private String url;
    private String protocol;
    private Map<String, String> headers;
    private Map<String, String[]> params;
    private String queryString;
}
