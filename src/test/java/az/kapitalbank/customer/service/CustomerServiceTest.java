package az.kapitalbank.customer.service;

import static az.kapitalbank.customer.common.TestConstants.CUSTOMER_ENTITY;
import static az.kapitalbank.customer.common.TestConstants.CUSTOMER_ID;
import static az.kapitalbank.customer.common.TestConstants.VALID_CUSTOMER_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import az.kapitalbank.customer.dao.repository.CustomerRepository;
import az.kapitalbank.customer.exception.CustomerNotFoundException;
import az.kapitalbank.customer.model.dto.CustomerCreateResponseDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer_Should_ReturnSuccess() {
        given(customerRepository.save(CUSTOMER_ENTITY)).willReturn(CUSTOMER_ENTITY);

        customerService.createCustomer(VALID_CUSTOMER_REQUEST);

        then(customerRepository).should().save(CUSTOMER_ENTITY);
    }

    @Test
    void getCustomer_Should_ReturnSuccess() {
        given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(CUSTOMER_ENTITY));

        CustomerCreateResponseDto actualResult = customerService.getCustomer(CUSTOMER_ID);
        assertNotNull(actualResult);

        then(customerRepository).should().findById(CUSTOMER_ID);
    }

    @Test
    void getCustomer_Should_ThrowCustomerNotFoundException_WhenNotExistsInDb() {
        given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.empty());

        CustomerNotFoundException ex = assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomer(CUSTOMER_ID));
        assertNotNull(ex);
        assertEquals(String.format("Customer not found with ID [%s]", CUSTOMER_ID),
                ex.getMessage());

        then(customerRepository).should().findById(CUSTOMER_ID);
    }
}
