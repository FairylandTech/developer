/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-06 06:03:04 UTC+08:00
 ****************************************************/
package host.fairy.infrastructure.component.jwt;

import host.fairy.infrastructure.config.properties.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Beau Dean
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;
}
