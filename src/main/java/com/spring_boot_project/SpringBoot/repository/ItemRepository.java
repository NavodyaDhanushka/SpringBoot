package com.spring_boot_project.SpringBoot.repository;

import com.spring_boot_project.SpringBoot.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
