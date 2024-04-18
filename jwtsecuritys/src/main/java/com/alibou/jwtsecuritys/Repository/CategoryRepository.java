package com.alibou.jwtsecuritys.Repository;


import com.alibou.jwtsecuritys.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
