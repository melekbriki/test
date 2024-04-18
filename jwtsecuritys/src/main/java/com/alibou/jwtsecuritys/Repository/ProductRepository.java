package com.alibou.jwtsecuritys.Repository;

import com.alibou.jwtsecuritys.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
