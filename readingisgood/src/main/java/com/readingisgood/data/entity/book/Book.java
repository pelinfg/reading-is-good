package com.readingisgood.data.entity.book;

import lombok.Data;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "book")
@Check(constraints = "price >= 0")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String isbn;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String author;

    private String publisher;

    @Length(max = 1000)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book")
    private BookStock bookStock;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double price;
}
