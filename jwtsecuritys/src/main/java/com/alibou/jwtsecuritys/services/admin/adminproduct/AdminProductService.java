package com.alibou.jwtsecuritys.services.admin.adminproduct;

import com.alibou.jwtsecuritys.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();
}
