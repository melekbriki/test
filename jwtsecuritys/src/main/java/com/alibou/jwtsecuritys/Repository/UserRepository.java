package com.alibou.jwtsecuritys.Repository;

import java.util.Optional;

import com.alibou.jwtsecuritys.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}