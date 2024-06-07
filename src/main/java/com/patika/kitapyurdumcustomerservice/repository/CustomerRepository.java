package com.patika.kitapyurdumcustomerservice.repository;

import com.patika.kitapyurdumcustomerservice.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {

    private List<Customer> customerList = new ArrayList<>();

    public void save(Customer customer) {
        customerList.add(customer);
    }

    public List<Customer> getAll() {
        return customerList;
    }

    public Optional<Customer> findById(Long id) {
        return getAll().stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    public Optional<Customer> findByEmail(String email) {
        return getAll().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();
    }
}
