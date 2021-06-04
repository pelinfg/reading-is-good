package com.readingisgood.customer;

import com.readingisgood.AbstractTestBase;
import com.readingisgood.data.entity.customer.Customer;
import com.readingisgood.data.entity.customer.CustomerRespository;
import com.readingisgood.service.customer.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

public class CustomerServiceTest extends AbstractTestBase {

    @Autowired
    private CustomerRespository customerRespository;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService(customerRespository);
        Customer firstCustomer = new Customer();
        firstCustomer.setFirstName("TestUser");
        firstCustomer.setLastName("Person");
        firstCustomer.setUserName("test_user123");
        firstCustomer.setAddress("test address");
        firstCustomer.setPassword("12345");
        firstCustomer.setEmail("test2@email.com");

        customerService.createCustomer(firstCustomer);
        customerRespository.flush();
    }

    @AfterEach
    public void cleanUp() {
        entityManager.clear();
    }

    @Test
    public void testInvalidEmail(){
        Customer newCustomer = new Customer();
        newCustomer.setEmail("myMAil.com");
        Set<ConstraintViolation<Customer>> violations = validator.validate(newCustomer);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testWhenNoUserName() {
        Customer c = new Customer();
        c.setUserName("");
        Set<ConstraintViolation<Customer>> violations = validator.validate(c);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Injected components are not null.")
    public void injectedComponentsAreNotNull() {
        assertThat(customerRespository).isNotNull();
        assertThat(customerService).isNotNull();
        assertThat(validator).isNotNull();
    }


}
