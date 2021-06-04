package com.readingisgood.service.order;

import com.readingisgood.data.entity.order.Order;
import com.readingisgood.data.entity.order.OrderItem;
import com.readingisgood.data.entity.order.OrderStatus;
import com.readingisgood.data.entity.order.repository.OrderRepository;
import com.readingisgood.data.model.order.OrderNewStatus;
import com.readingisgood.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private StockService stockService;

    public void createOrder(Order order) throws Exception {
        Set<OrderItem> orderItemList = order.getOrderItems();
        Map<Long, Integer> bookQuantityMap = new HashMap<>();
        BigDecimal total = new BigDecimal("0.0");

        for (OrderItem item : orderItemList) {
            bookQuantityMap.put(item.getBookId(), item.getQuantity());
            BigDecimal price = new BigDecimal(item.getPrice().toString());
            BigDecimal quantity = new BigDecimal(item.getQuantity());
            total = total.add(price.multiply(quantity));
            item.setOrder(order);
        }

        if (!stockService.checkStockAndDecreaseStock(bookQuantityMap).isEmpty()) {
            order.setStatus(OrderStatus.SUCCESSFUL);
            order.setOrderCost(total.doubleValue());
            log.info("Saving order : {}", order.toString());
            orderRepository.save(order);
        } else {
            log.info("Empty order occurred, not saving.");
        }
    }

    public OrderNewStatus updateOrder(Long orderId, OrderStatus newStatus) {
        orderRepository.updateStatus(orderId, newStatus);
        return new OrderNewStatus(orderId, newStatus);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long id) {
        Optional<Order> res = orderRepository.findById(id);
        if (!res.isPresent()) {
            throw new NoSuchElementException("Book with id " + id + " does not exist.");
        }
        return res.get();
    }

    public List<Order> findAllByDate(Date start, Date end) {
        return orderRepository.findBetweenDate(start, end);
    }

    public List<Order> findOrderItemsMonthly(Long customerId, Integer month) {
        List<Order> orders = orderRepository.findOrderMonthly(customerId, month);
        return orders;
    }

    public List<Order> getCustomerAllOrders(Long customerId) {
        return orderRepository.getCustomerAllOrders(customerId);
    }
}
