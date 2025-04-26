package az.kapitalbank.customer.service;

import static az.kapitalbank.customer.common.TestConstants.CUSTOMER_ENTITY;
import static az.kapitalbank.customer.common.TestConstants.CUSTOMER_ENTITY_WITH_BALANCE_200;
import static az.kapitalbank.customer.common.TestConstants.CUSTOMER_ENTITY_WITH_BALANCE_300;
import static az.kapitalbank.customer.common.TestConstants.CUSTOMER_ID;
import static az.kapitalbank.customer.common.TestConstants.VALID_BALANCE_CHANGE_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import az.kapitalbank.customer.dao.repository.CustomerRepository;
import az.kapitalbank.customer.exception.CustomerNotFoundException;
import az.kapitalbank.customer.exception.InsufficientFundsException;
import az.kapitalbank.customer.model.dto.BalanceChangeRequestDto;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private InternalBalanceService balanceService;

    @Test
    void increaseBalance_Should_ReturnSuccess() {
        given(customerRepository.findByIdForUpdate(CUSTOMER_ID)).willReturn(
                Optional.of(CUSTOMER_ENTITY_WITH_BALANCE_300));

        balanceService.increaseBalance(CUSTOMER_ID, VALID_BALANCE_CHANGE_REQUEST);
        assertEquals(BigDecimal.valueOf(350), CUSTOMER_ENTITY_WITH_BALANCE_300.getBalance());

        then(customerRepository).should().findByIdForUpdate(CUSTOMER_ID);
    }

    @Test
    void increaseBalance_Should_ThrowCustomerNotFoundException_WhenNotExistsInDb() {
        given(customerRepository.findByIdForUpdate(CUSTOMER_ID)).willReturn(Optional.empty());

        CustomerNotFoundException ex = assertThrows(CustomerNotFoundException.class,
                () -> balanceService.increaseBalance(CUSTOMER_ID, VALID_BALANCE_CHANGE_REQUEST));
        assertNotNull(ex);
        assertEquals(String.format("Customer not found with ID [%s]", CUSTOMER_ID),
                ex.getMessage());

        then(customerRepository).should().findByIdForUpdate(CUSTOMER_ID);
    }

    @Test
    void decreaseBalance_Should_ReturnSuccess() {
        given(customerRepository.findByIdForUpdate(CUSTOMER_ID))
                .willReturn(Optional.of(CUSTOMER_ENTITY_WITH_BALANCE_200));

        balanceService.decreaseBalance(CUSTOMER_ID, VALID_BALANCE_CHANGE_REQUEST);
        assertEquals(BigDecimal.valueOf(150), CUSTOMER_ENTITY_WITH_BALANCE_200.getBalance());

        then(customerRepository).should().findByIdForUpdate(CUSTOMER_ID);
    }

    @Test
    void decreaseBalance_Should_ThrowCustomerNotFoundException_WhenNotExistsInDb() {
        given(customerRepository.findByIdForUpdate(CUSTOMER_ID)).willReturn(Optional.empty());

        CustomerNotFoundException ex = assertThrows(CustomerNotFoundException.class,
                () -> balanceService.decreaseBalance(CUSTOMER_ID, VALID_BALANCE_CHANGE_REQUEST));
        assertNotNull(ex);
        assertEquals(String.format("Customer not found with ID [%s]", CUSTOMER_ID),
                ex.getMessage());

        then(customerRepository).should().findByIdForUpdate(CUSTOMER_ID);
    }

    @Test
    void decreaseBalance_Should_ThrowInsufficientFundsException_WhenBalanceIsInsufficient() {
        given(customerRepository.findByIdForUpdate(CUSTOMER_ID)).willReturn(Optional.of(CUSTOMER_ENTITY));
        BalanceChangeRequestDto largeAmountRequest = BalanceChangeRequestDto.builder()
                .transactionId(VALID_BALANCE_CHANGE_REQUEST.getTransactionId())
                .amount(BigDecimal.valueOf(200))
                .build();

        InsufficientFundsException ex = assertThrows(InsufficientFundsException.class,
                () -> balanceService.decreaseBalance(CUSTOMER_ID, largeAmountRequest));
        assertNotNull(ex);
        assertEquals("Insufficient funds: Available balance = [100], Requested amount = [200]",
                ex.getMessage());

        then(customerRepository).should().findByIdForUpdate(CUSTOMER_ID);
    }
} 