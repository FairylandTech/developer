/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-29 15:56:37 UTC+08:00
 ****************************************************/
package host.fairy.service;

import host.fairy.dto.category.CategoryDTO;
import host.fairy.dto.category.CategoryQueryDTO;
import host.fairy.entity.CategoryEntity;
import host.fairy.result.ListRocord;

/**
 * @author Beau Dean
 */
public interface CategoryService {
    ListRocord<CategoryEntity> queryList(CategoryQueryDTO categoryQueryDTO);
    
    CategoryEntity queryByid(Integer id);
    
    Boolean add(CategoryDTO categoryDTO);
}
