package com.readingisgood.data.entity.order.repository;

import com.readingisgood.data.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
