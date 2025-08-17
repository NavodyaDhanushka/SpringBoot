package com.spring_boot_project.SpringBoot.repository;

import com.spring_boot_project.SpringBoot.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
