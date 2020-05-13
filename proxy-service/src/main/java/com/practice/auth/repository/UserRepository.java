package com.practice.auth.repository;

import com.practice.auth.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Long>{
    ApplicationUser findByUsername(String name);
}
