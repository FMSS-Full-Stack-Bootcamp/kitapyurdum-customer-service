package com.patika.kitapyurdumcustomerservice.service;

import com.patika.kitapyurdumcustomerservice.dto.request.CustomerSaveRequest;
import com.patika.kitapyurdumcustomerservice.exception.ExceptionMessages;
import com.patika.kitapyurdumcustomerservice.exception.KitapYurdumException;
import com.patika.kitapyurdumcustomerservice.model.AccountType;
import com.patika.kitapyurdumcustomerservice.model.Customer;
import com.patika.kitapyurdumcustomerservice.repository.CustomerRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldThrowException_whenUserAlreadyExist() {

        String email = "bilisimio@gmail.com";

        //given
        CustomerSaveRequest request = Instancio.of(CustomerSaveRequest.class)
                .set(field(CustomerSaveRequest::getEmail), email)
                .set(field(CustomerSaveRequest::getName), "bilisimio")
                .create();

        Mockito.when(customerRepository.findByEmail(email))
                .thenReturn(Optional.of(Instancio.of(Customer.class)
                        .set(field(Customer::getEmail), email)
                        .set(field(Customer::getIsActive), false)
                        .set(field(Customer::getAccountType), AccountType.PLATINUM)
                        .create()));

        //when
        KitapYurdumException exception = assertThrows(KitapYurdumException.class, () -> customerService.save(request));

        //then
        assertThat(exception).hasMessage(ExceptionMessages.EMAIL_ALREADY_EXIST);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void shouldSaveCustomer() {

        String email = "bilisimio@gmail.com";

        //given
        CustomerSaveRequest request = Instancio.of(CustomerSaveRequest.class)
                .set(field(CustomerSaveRequest::getEmail), email)
                .set(field(CustomerSaveRequest::getName), "bilisimio")
                .supply(all(LocalDate.class), () -> LocalDate.now()) // bütün LocalDate'lere istediğimiz değeri veriyoruz
                .withNullable(field(CustomerSaveRequest::getSurname))
                .withNullable(field(CustomerSaveRequest::getProvince))
                .create();

        Mockito.when(customerRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        //when
        assertDoesNotThrow(() -> customerService.save(request));

        //then
        verify(customerRepository, times(1)).save(Mockito.any(Customer.class));
    }
}