package com.alibou.jwtsecuritys.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


@Data
@Getter
@Setter
public class ProductDto {

    private Long id;

    private String name;

    private Long price;

    private String description;

    private byte[] byteImg;

    private Long categoryId;

    private MultipartFile img;



}
