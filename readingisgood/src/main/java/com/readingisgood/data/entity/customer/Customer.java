package com.readingisgood.data.entity.customer;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    @NotEmpty
    private String userName;

    @Column(unique = true)
    @Email(message = "Enter a valid email address.")
    private String email;

    private String address;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;
}
