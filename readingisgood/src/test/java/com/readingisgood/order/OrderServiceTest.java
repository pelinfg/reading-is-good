package com.readingisgood.order;

import com.readingisgood.AbstractTestBase;
import com.readingisgood.data.entity.order.Order;
import com.readingisgood.data.entity.order.OrderItem;
import com.readingisgood.data.entity.order.OrderStatus;
import com.readingisgood.data.entity.order.repository.OrderRepository;
import com.readingisgood.service.order.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest extends AbstractTestBase {

    @Autowired
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService(orderRepository);
        Order order = new Order();
        order.setCreatedAt(new Date());
        order.setCustomerId(1);
        order.setStatus(OrderStatus.SUCCESSFUL);
        order.setOrderCost(100d);
        OrderItem item = new OrderItem();
        item.setBookId(1L);
        item.setPrice(10.00);
        item.setQuantity(5);
        item.setOrder(order);
        Set<OrderItem> items = new HashSet<>();
        items.add(item);
        order.setOrderItems(items);
        orderRepository.save(order);
    }

    @AfterEach
    public void cleanUp() {
        entityManager.clear();
    }

    @Test
    public void testFindOrderById() {
        Long id = orderRepository.findAll().get(0).getId();
        Order order = orderService.findOrderById(id);
        assertThat(order.getId()).isEqualTo(id);
    }

    @Test
    public void testFindOrdersBetweenDates() {
        Date date = orderRepository.findAll().get(0).getCreatedAt();
        Date start = Date.from(date.toInstant());
        Date end = Date.from(start.toInstant());
        List<Order> orders = orderService.findAllByDate(start, end);
        assertThat(orders).size().isGreaterThan(0);
    }

    @Test
    public void testFindCustomerOrders() {
        Long custId = orderRepository.findAll().get(0).getCustomerId();
        List<Order> orders = orderService.getCustomerAllOrders(custId);
        assertThat(orders).size().isGreaterThan(0);
    }

    @Test
    void testfindOrdersMonthly() {
        Order c = orderRepository.findAll().get(0);
        LocalDate localDate = c.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        List<Order> orders = orderRepository.findOrderMonthly(c.getCustomerId(), month);
        assertThat(orders).size().isGreaterThan(0);
    }

    @Test
    @DisplayName("Injected components are not null.")
    public void injectedComponentsAreNotNull() {
        assertThat(orderRepository).isNotNull();
        assertThat(orderService).isNotNull();
        assertThat(validator).isNotNull();
    }
}
