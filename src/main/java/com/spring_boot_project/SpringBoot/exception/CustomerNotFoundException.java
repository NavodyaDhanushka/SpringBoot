package com.spring_boot_project.SpringBoot.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(int id) {
        super("Customer with id " + id + " not found");
    }
}
