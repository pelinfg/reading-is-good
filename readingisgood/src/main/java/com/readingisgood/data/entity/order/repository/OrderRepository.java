package com.readingisgood.data.entity.order.repository;

import com.readingisgood.data.entity.order.Order;
import com.readingisgood.data.entity.order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value="update orders set status = :status where id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Long id, @Param("status") OrderStatus status);

    @Query("FROM Order order WHERE order.createdAt between :start and :end")
    List<Order> findBetweenDate(@Param("start") Date start, @Param("end") Date end);

    @Query(value = "select * from orders o WHERE MONTH(o.created_at)= :month and o.customer_id = :customerId", nativeQuery = true)
    List<Order> findOrderMonthly(@Param("customerId") Long customerId, @Param("month") Integer month);

    @Query("FROM Order order WHERE order.customerId = :customerId")
    List<Order> getCustomerAllOrders(@Param("customerId") Long customerId);
}
