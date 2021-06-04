package com.readingisgood.data.model.order;

import com.readingisgood.data.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderNewStatus {
    private Long orderId;

    private OrderStatus status;
}
