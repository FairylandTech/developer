/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-02 04:56:56 UTC+08:00
 ****************************************************/
package host.fairy.service;

import host.fairy.model.RequestInfo;
import host.fairy.model.SimpleUser;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
public interface SimpleService {
    RequestInfo getRequestInfo(HttpServletRequest request);
    
    List<SimpleUser> getSimpleUsers();
}
