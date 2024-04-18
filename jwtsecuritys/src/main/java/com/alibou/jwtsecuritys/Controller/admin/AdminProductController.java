package com.alibou.jwtsecuritys.Controller.admin;


import com.alibou.jwtsecuritys.dto.ProductDto;
import com.alibou.jwtsecuritys.services.admin.adminproduct.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/product")

    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productDto1= adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos=adminProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }
}
