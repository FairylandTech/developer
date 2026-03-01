/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-01 21:50:30 UTC+08:00
 ****************************************************/
package host.fairy.controller;

import host.fairy.model.http.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@RestController
@RequestMapping("/request")
public class RequestController {
    
    @GetMapping("/info")
    public ResponseBody<Map<String, Object>> request(HttpServletRequest request) {
        
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, String> headers = new HashMap<>();
        
        data.put("method", request.getMethod());
        data.put("uri", request.getRequestURI());
        data.put("url", request.getRequestURL().toString());
        data.put("protocol", request.getProtocol());
        
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            headers.put(headerName, request.getHeader(headerName));
        });
        
        data.put("headers", headers);
        
        // URL Params
        data.put("name", request.getParameter("name"));
        // URL Query String
        data.put("query", request.getQueryString());
        
        return ResponseBody.success(data);
    }
}
