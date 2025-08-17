package com.spring_boot_project.SpringBoot.repository;

import com.spring_boot_project.SpringBoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
