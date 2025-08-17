package com.spring_boot_project.SpringBoot.controller;


import com.spring_boot_project.SpringBoot.exception.CustomerNotFoundException;
import com.spring_boot_project.SpringBoot.model.Customer;
import com.spring_boot_project.SpringBoot.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/addcustomer")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/allcustomers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable int id) {
        return customerRepository.findById(id)
        .orElseThrow(()->new CustomerNotFoundException(id));
    }

    @PutMapping("/editcustomer/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        return customerRepository.findById(id)
                .map(customer1 -> {
                    customer1.setName(customer.getName());
                    customer1.setNic(customer.getNic());
                    customer1.setContactNo(customer.getContactNo());
                    customer1.setAddress(customer.getAddress());
                    return customerRepository.save(customer1);
                    }).orElseThrow(()->new CustomerNotFoundException(id));
    }

    @DeleteMapping("/deletecustomer/{id}")
    public String deleteCustomer(@PathVariable int id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
        return "Customer with id " +id+ " has been deleted success";
    }

}
