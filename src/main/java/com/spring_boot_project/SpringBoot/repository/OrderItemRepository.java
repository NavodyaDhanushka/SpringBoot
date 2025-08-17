package com.spring_boot_project.SpringBoot.repository;

import com.spring_boot_project.SpringBoot.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
