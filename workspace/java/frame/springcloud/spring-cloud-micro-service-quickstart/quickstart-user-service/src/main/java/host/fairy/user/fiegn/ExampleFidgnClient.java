/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-08 18:56:36 UTC+08:00
 ****************************************************/
package host.fairy.user.fiegn;

import host.fairy.fairylandfuture.common.web.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Beau Dean
 * @version 1.0
 */
@FeignClient(value = "quickstart-example-service")
public interface ExampleFidgnClient {
    
    @GetMapping("/api/example")
    Response<String> example();
}
