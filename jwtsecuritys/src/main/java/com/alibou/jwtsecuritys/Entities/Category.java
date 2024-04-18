package com.alibou.jwtsecuritys.Entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    private String name;

    @Lob
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Product> products;


}
