package com.alibou.jwtsecuritys.services.admin.category;


import com.alibou.jwtsecuritys.Entities.Category;
import com.alibou.jwtsecuritys.Repository.CategoryRepository;
import com.alibou.jwtsecuritys.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private  final CategoryRepository categoryRepository;

    public Category  createcategory(CategoryDto categoryDto ){

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return  categoryRepository.findAll();
    }
}
