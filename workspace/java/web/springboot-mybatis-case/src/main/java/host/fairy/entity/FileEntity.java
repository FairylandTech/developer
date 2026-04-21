/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-26 16:24:41 UTC+08:00
 ****************************************************/
package host.fairy.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Beau Dean
 */
@Data
public class FileEntity {
    private Integer id;
    
    private String filename;
    
    private String type;
    
    private String name;
    
    private String extension;
    
    private Long size;
    
    private String path;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Boolean existed;
}
