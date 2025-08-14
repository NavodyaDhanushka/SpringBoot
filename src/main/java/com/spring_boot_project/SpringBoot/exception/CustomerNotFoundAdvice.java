package com.spring_boot_project.SpringBoot.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomerNotFoundAdvice {

    @RequestMapping
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> exceptionHandler(CustomerNotFoundException exception) {
        Map<String, String> errormap = new HashMap<>();
        errormap.put("Error", exception.getMessage());
        return errormap;
    }
}
