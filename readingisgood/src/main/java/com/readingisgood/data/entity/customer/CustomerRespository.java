package com.readingisgood.data.entity.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRespository extends JpaRepository<Customer, Long> {
}

