package com.readingisgood.service.statistic;

import com.readingisgood.data.entity.order.Order;
import com.readingisgood.data.entity.order.OrderItem;
import com.readingisgood.data.model.statistic.StatisticResponse;
import com.readingisgood.service.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class StatisticService {

    private final OrderService orderService;

    public StatisticResponse getStatisticMonthly(Long customerId, Integer month)  {
        List<Order> orders = orderService.findOrderItemsMonthly(customerId, month);
        if(orders.isEmpty()) {
            throw new NoSuchElementException("No order found in this month.");
        }
        StatisticResponse res = new StatisticResponse();
        res.setMonth(new DateFormatSymbols().getMonths()[month-1]);
        res.setOrderCount(orders.size());
        List<OrderItem> orderItemList = new ArrayList<>();
        double amount = 0d;
        for (Order o : orders) {
            orderItemList.addAll(o.getOrderItems());
            amount += o.getOrderCost();
        }

        int total_books = 0;
        for(OrderItem item: orderItemList) {
            total_books += item.getQuantity();
        }

        res.setAmount(amount);
        res.setBookCount(total_books);
        return res;
    }

}
