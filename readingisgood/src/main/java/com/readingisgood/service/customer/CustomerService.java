package com.readingisgood.service.customer;

import com.readingisgood.data.entity.customer.Customer;
import com.readingisgood.data.entity.customer.CustomerRespository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRespository repository;

    public void createCustomer(Customer customer) {
        log.info("Saving customer : {}", customer.toString());
        repository.save(customer);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }
}
