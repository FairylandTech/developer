/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-03-07 UTC+08:00
 ****************************************************/
package host.fairy.application.service.impl;

import host.fairy.application.service.SimpleApplicationService;
import host.fairy.domain.model.RequestInfo;
import host.fairy.domain.model.SimpleUserModel;
import host.fairy.domain.repository.SimpleUserRepository;
import host.fairy.domain.service.SimpleUserDomainService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import java.util.stream.Collectors;

/**
 * SimpleUser 应用服务实现
 * 负责编排domain层的对象、管理事务、调用repository和domainService
 * 业务逻辑不在这里，而是在domain/model中
 * 
 * @author Beau Dean
 * @version 1.0
 */
@Service
@Transactional
public class SimpleApplicationServiceImpl implements SimpleApplicationService {
    
    private final SimpleUserRepository repository;
    private final SimpleUserDomainService domainService;
    
    @Autowired
    public SimpleApplicationServiceImpl(SimpleUserRepository repository, 
                                       SimpleUserDomainService domainService) {
        this.repository = repository;
        this.domainService = domainService;
    }
    
    @Override
    @Transactional(readOnly = true)
    public RequestInfo getRequestInfo(HttpServletRequest request) {
        HashMap<String, String> headers = new HashMap<>();
        
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> 
                    headers.put(headerName, request.getHeader(headerName)));
        
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
    @Transactional(readOnly = true)
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
                            .createdAt(LocalDateTime.parse(parts[5].trim(), 
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build();
                }).collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SimpleUserModel> getSimpleUsersFromDatabase() {
        return repository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public SimpleUserModel getUserById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    @Transactional
    public SimpleUserModel createUser(SimpleUserModel user) {
        // 使用DomainService进行业务验证
        domainService.validateUsernameUnique(user.getUsername());
        
        if (!domainService.isPasswordStrong(user.getPassword())) {
            throw new IllegalArgumentException("密码强度不足，至少需要6个字符");
        }
        
        // 保存到数据库
        return repository.save(user);
    }
    
    // ===== 私有方法 =====
    
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
