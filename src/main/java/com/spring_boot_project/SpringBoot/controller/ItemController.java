package com.spring_boot_project.SpringBoot.controller;


import com.spring_boot_project.SpringBoot.exception.ItemNotFoundException;
import com.spring_boot_project.SpringBoot.model.Item;
import com.spring_boot_project.SpringBoot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired private ItemRepository itemRepository;

    @PostMapping("/additmes")
    public Item addItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @GetMapping("/allitems")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/item/{id}")
    public Item getItem(@PathVariable int id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }




}
