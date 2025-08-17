package com.spring_boot_project.SpringBoot.controller;

import com.spring_boot_project.SpringBoot.exception.ItemNotFoundException;
import com.spring_boot_project.SpringBoot.model.Item;
import com.spring_boot_project.SpringBoot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // Add new item
    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item savedItem = itemRepository.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // Get all items
    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    // Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        return ResponseEntity.ok(item);
    }

    // Update item
    @PutMapping("/edit/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item item) {
        Item updatedItem = itemRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setCode(item.getCode());
                    existingItem.setPrice(item.getPrice());
                    existingItem.setQuantity(item.getQuantity());
                    return itemRepository.save(existingItem);
                }).orElseThrow(() -> new ItemNotFoundException(id));
        return ResponseEntity.ok(updatedItem);
    }

    // Delete item
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException(id);
        }
        itemRepository.deleteById(id);
        return ResponseEntity.ok("Item with id " + id + " has been deleted successfully");
    }
}
