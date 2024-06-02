package com.patika.kitapyurdumcustomerservice.service;

import com.patika.kitapyurdumcustomerservice.converter.CustomerConverter;
import com.patika.kitapyurdumcustomerservice.dto.request.CustomerSaveRequest;
import com.patika.kitapyurdumcustomerservice.exception.ExceptionMessages;
import com.patika.kitapyurdumcustomerservice.exception.KitapYurdumException;
import com.patika.kitapyurdumcustomerservice.model.AccountType;
import com.patika.kitapyurdumcustomerservice.model.Customer;
import com.patika.kitapyurdumcustomerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository; //yönetim artık spring'te

    public void save(CustomerSaveRequest request) {

        Optional<Customer> foundCustomer = customerRepository.findByEmail(request.getEmail());

        if (foundCustomer.isPresent()) {
            log.error(ExceptionMessages.EMAIL_ALREADY_EXIST);
            throw new KitapYurdumException(ExceptionMessages.EMAIL_ALREADY_EXIST);
        }

        Customer customer = CustomerConverter.toCustomer(request);

        customerRepository.createCustomer(customer);

        log.info("customer created. {}", customer.getEmail());
    }

    public List<Customer> getCustomerList() {
        log.info("customer listed. ");
        return customerRepository.getCustomerList();
    }

    public void changeAccountType(String email, AccountType accountType) {

        Optional<Customer> foundCustomer = getCustomerList()
                .stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();

        if (foundCustomer.isPresent()) {
            foundCustomer.get().setAccountType(accountType);
        }
    }

    public void changeAccountTypeByCredit(String email, AccountType accountType) { //ödev

    }

    public Customer getById(Long id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);

        if (foundCustomer.isEmpty()) {
            log.error(ExceptionMessages.CUSTOMER_NOT_FOUND);
            throw new KitapYurdumException(ExceptionMessages.CUSTOMER_NOT_FOUND);
        }

        return foundCustomer.get();
    }

    public Customer getByEmail(String email) {

        Optional<Customer> foundCustomer = customerRepository.findByEmail(email);

        if (!foundCustomer.get().getIsActive()) {
            log.error(ExceptionMessages.CUSTOMER_NOT_ACTIVE);
            throw new KitapYurdumException(ExceptionMessages.CUSTOMER_NOT_ACTIVE);
        }
        log.info("customer found. {}", email);
        return foundCustomer.get();
    }
}
