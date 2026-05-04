/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-25 11:07:56 UTC+08:00
 ****************************************************/
package com.fairyland.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Beau Dean
 */
@Data
public class DepartmentEntity {
    /**
     * id 主键ID
     */
    private Integer id;
    
    /**
     * name 部门名称
     */
    private String name;
    
    /**
     * createdTime 创建时间
     */
    private LocalDateTime createdTime;
    
    /**
     * updatedTime 修改时间
     */
    private LocalDateTime updatedTime;
}
