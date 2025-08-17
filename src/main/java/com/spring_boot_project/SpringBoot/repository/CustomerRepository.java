package com.spring_boot_project.SpringBoot.repository;

import com.spring_boot_project.SpringBoot.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
