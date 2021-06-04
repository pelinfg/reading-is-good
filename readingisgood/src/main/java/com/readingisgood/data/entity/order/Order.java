package com.readingisgood.data.entity.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "orderItems")
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private long id;

    private long customerId;

    @CreationTimestamp
    private Date createdAt;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", orderItems=" + orderItems +
                ", orderCost=" + orderCost +
                '}';
    }

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy="order", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<OrderItem> orderItems = new HashSet<>(0);

    @Column(precision = 10, scale = 2)
    private Double orderCost;

}
