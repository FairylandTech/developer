/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-25 11:42:40 UTC+08:00
 ****************************************************/
package com.fairyland.service;

import com.fairyland.entity.EmployeeEntity;
import com.fairyland.model.EmployeeModel;

import java.util.List;

/**
 * @author Beau Dean
 */
public interface EmployeeService {
    
    public List<EmployeeEntity> getList(EmployeeModel model);
}
