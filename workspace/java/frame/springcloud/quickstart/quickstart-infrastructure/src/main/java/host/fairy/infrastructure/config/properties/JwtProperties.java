/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-06 06:03:51 UTC+08:00
 ****************************************************/
package host.fairy.infrastructure.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private String secretKey;
    private long expirationTime;
    private String tokenName;
}
