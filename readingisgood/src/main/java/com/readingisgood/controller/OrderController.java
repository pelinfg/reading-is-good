package com.readingisgood.controller;

import com.readingisgood.data.entity.order.Order;
import com.readingisgood.data.model.order.OrderNewStatus;
import com.readingisgood.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        try {
            orderService.createOrder(order);
            return new ResponseEntity<>("Order created. ", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Could not create new order: {}", e.toString());
            return new ResponseEntity<>("Order creation failed!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listAll")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/listAllByDate")
    public List<Order> findAll(@RequestBody Map<String, Date> request) {
        log.info(request);
        return orderService.findAllByDate(request.get("startDate"), request.get("endDate"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        try {
            Order order = orderService.findOrderById(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("Order with id {} is not existed.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateOrderStatus")
    public ResponseEntity<OrderNewStatus> updateOrderStatus(@RequestBody OrderNewStatus request) {
        try {
            OrderNewStatus res = orderService.updateOrder(request.getOrderId(), request.getStatus());
            log.info("Order updated successfully!");
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Could not update the order: {}", e.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listCustomerOrders")
    public List<Order> listCustomerOrders(@RequestBody Map<String, Long> request) {
        return orderService.getCustomerAllOrders(request.get("customerId"));
    }
}
