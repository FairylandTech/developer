/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-02 04:57:24 UTC+08:00
 ****************************************************/
package host.fairy.service.impl;

import host.fairy.mapper.SimpleUserMapper;
import host.fairy.model.RequestInfo;
import host.fairy.model.SimpleUserModel;
import host.fairy.service.SimpleService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
@Service
public class SimpleServiceImpl implements SimpleService {
    
    private final SimpleUserMapper simpleUserMapper;
    
    @Autowired
    public SimpleServiceImpl(SimpleUserMapper simpleUserMapper) {
        this.simpleUserMapper = simpleUserMapper;
    }
    
    @Override
    public RequestInfo getRequestInfo(HttpServletRequest request) {
        HashMap<String, String> headers = new HashMap<>();
        
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> headers.put(headerName, request.getHeader(headerName)));
        
        return RequestInfo.builder()
                .method(request.getMethod())
                .uri(request.getRequestURI())
                .url(request.getRequestURL().toString())
                .protocol(request.getProtocol())
                .headers(headers)
                .params(request.getParameterMap())
                .queryString(request.getQueryString())
                .build();
    }
    
    @Override
    public List<SimpleUserModel> getSimpleUsers() {
        return this.readLines(new File(this.getResource("user.txt")))
                .stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    
                    return SimpleUserModel.builder()
                            .id(Long.parseLong(parts[0].trim()))
                            .username(parts[1].trim())
                            .password(parts[2].trim())
                            .name(parts[3].trim())
                            .age(Integer.parseInt(parts[4].trim()))
                            .createdAt(LocalDateTime.parse(parts[5].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build();
                }).toList();
    }
    
    @Override
    public List<SimpleUserModel> getSimpleUsersFromDatabase() {
        return this.simpleUserMapper.selectSimpleUsers();
    }
    
    private URI getResource(String resourceName) {
        URL resource = this.getClass().getClassLoader().getResource(resourceName);
        
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }
        
        try {
            return resource.toURI();
        } catch (URISyntaxException exception) {
            throw new RuntimeException("Invalid URI syntax for resource: " + resourceName, exception);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to get resource: " + resourceName, exception);
        }
    }
    
    private List<String> readLines(File file) {
        try {
            return FileUtils.readLines(file, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to read file: " + file.getAbsolutePath(), exception);
        } catch (Exception exception) {
            throw new RuntimeException("Unexpected error while reading file: " + file.getAbsolutePath(), exception);
        }
    }
}
