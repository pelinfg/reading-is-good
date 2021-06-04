package com.readingisgood.data.entity.book;

import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book_stock")
@Check(constraints = "stock >= 0")
public class BookStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(columnDefinition = "integer default 0")
    private int stock;
}
