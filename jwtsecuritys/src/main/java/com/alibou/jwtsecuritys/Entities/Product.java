package com.alibou.jwtsecuritys.Entities;

import com.alibou.jwtsecuritys.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product")
@Data
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;


    public ProductDto getDto(){
       ProductDto productDto = new ProductDto();
       productDto.setId(id);
       productDto.setName(name);
       productDto.setPrice(price);
       productDto.setDescription(description);
        productDto.setByteImg(img);
        if (category != null) {
            productDto.setCategoryId(category.getId());
        } else {
            throw new IllegalStateException("Category is null");
        }
       return productDto;
   }
}
