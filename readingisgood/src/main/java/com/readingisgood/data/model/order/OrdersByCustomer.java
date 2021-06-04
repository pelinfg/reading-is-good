package com.readingisgood.data.model.order;

import com.readingisgood.data.entity.order.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrdersByCustomer {
    private Long customerId;

    private List<Order> allOrders;
}
