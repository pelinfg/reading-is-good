package com.readingisgood.controller;

import com.readingisgood.data.entity.customer.Customer;
import com.readingisgood.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping("/createCustomer")
    public ResponseEntity<String> createUser(@RequestBody Customer customer) {
        try {
            service.createCustomer(customer);
            return new ResponseEntity<>("User created successfully!", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Customer could not created: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listAll")
    public List<Customer> findAll() {
        return service.findAll();
    }

}
