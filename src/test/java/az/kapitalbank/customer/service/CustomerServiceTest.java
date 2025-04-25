package az.kapitalbank.customer.service;

import static az.kapitalbank.customer.common.TestConstants.CUSTOMER_ENTITY;
import static az.kapitalbank.customer.common.TestConstants.VALID_CUSTOMER_REQUEST;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import az.kapitalbank.customer.dao.repository.CustomerRepository;
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

}
