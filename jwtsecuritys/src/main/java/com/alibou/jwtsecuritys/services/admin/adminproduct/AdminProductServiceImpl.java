package com.alibou.jwtsecuritys.services.admin.adminproduct;


import com.alibou.jwtsecuritys.Entities.Category;
import com.alibou.jwtsecuritys.Entities.Product;
import com.alibou.jwtsecuritys.Repository.CategoryRepository;
import com.alibou.jwtsecuritys.Repository.ProductRepository;
import com.alibou.jwtsecuritys.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Long categoryId = productDto.getCategoryId();
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID must not be null");
        }

        // Find the category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(category); // Set the category

        // Handle image if not null
        if (productDto.getImg() != null) {
            product.setImg(productDto.getImg().getBytes());
        }

        // Save the product and return its DTO
        return productRepository.save(product).getDto();
    }




    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
