package com.spring_boot_project.SpringBoot.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Integer id) {
        super("Item with id " + id + " not found");
    }
}
