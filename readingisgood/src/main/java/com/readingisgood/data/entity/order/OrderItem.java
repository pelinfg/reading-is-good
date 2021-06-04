package com.readingisgood.data.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JsonBackReference
    private Order order;

    @Column(nullable=false)
    private Long bookId;

    private String title;

    @Column(nullable=false)
    private int quantity;

    @Min(value = 0)
    @Column(nullable = false, precision = 10, scale = 2)
    private Double price;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", title='" + title + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                "'}";
    }

}
