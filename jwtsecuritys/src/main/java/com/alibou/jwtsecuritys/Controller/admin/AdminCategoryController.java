package com.alibou.jwtsecuritys.Controller.admin;


import com.alibou.jwtsecuritys.Entities.Category;
import com.alibou.jwtsecuritys.dto.CategoryDto;
import com.alibou.jwtsecuritys.services.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping("category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto)
    {
        Category category = categoryService.createcategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    @GetMapping("categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
