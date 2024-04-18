package com.alibou.jwtsecuritys.services.admin.category;

import com.alibou.jwtsecuritys.Entities.Category;
import com.alibou.jwtsecuritys.dto.CategoryDto;

import java.util.List;

public interface CategoryService {



    Category createcategory(CategoryDto categoryDto );
     List<Category> getAllCategories();
}
