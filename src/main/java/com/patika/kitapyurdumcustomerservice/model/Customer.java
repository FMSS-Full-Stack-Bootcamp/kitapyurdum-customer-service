package com.patika.kitapyurdumcustomerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Integer credit;
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Address> addresses;
    private Boolean isActive;
    private AccountType accountType;

    public Customer(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
