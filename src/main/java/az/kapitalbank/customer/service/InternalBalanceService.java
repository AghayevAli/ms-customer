package az.kapitalbank.customer.service;

import static az.kapitalbank.customer.exception.constraint.ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE;
import static az.kapitalbank.customer.exception.constraint.ErrorMessage.INSUFFICIENT_BALANCE_MESSAGE;

import az.kapitalbank.customer.dao.entity.CustomerEntity;
import az.kapitalbank.customer.dao.repository.CustomerRepository;
import az.kapitalbank.customer.exception.CustomerNotFoundException;
import az.kapitalbank.customer.exception.InsufficientFundsException;
import az.kapitalbank.customer.model.dto.BalanceChangeRequestDto;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternalBalanceService {

    private final CustomerRepository customerRepository;

    @Transactional
    public void increaseBalance(Long customerId, BalanceChangeRequestDto request) {
        log.info("Received increase balance request: {}", request);
        CustomerEntity customer = getCustomer(customerId);

        BigDecimal updatedBalance = customer.getBalance().add(request.getAmount());
        customer.setBalance(updatedBalance);

        log.info("Balance increased: customerId={}, amount={}, newBalance={}",
                customer.getId(), request.getAmount(), updatedBalance);
    }

    @Transactional
    public void decreaseBalance(Long customerId, BalanceChangeRequestDto request) {
        log.info("Received decrease balance request: {}", request);

        CustomerEntity customer = getCustomer(customerId);

        validateSufficientBalance(customer.getBalance(), request.getAmount());

        BigDecimal updatedBalance = customer.getBalance().subtract(request.getAmount());
        customer.setBalance(updatedBalance);

        log.info("Balance decreased: customerId={}, amount={}, newBalance={}",
                customer.getId(), request.getAmount(), updatedBalance);
    }

    private CustomerEntity getCustomer(Long customerId) {
        return customerRepository.findByIdForUpdate(customerId)
                .orElseThrow(() -> CustomerNotFoundException.of(CUSTOMER_NOT_FOUND_MESSAGE, customerId));
    }

    private void validateSufficientBalance(BigDecimal balance, BigDecimal requestedAmount) {
        if (balance.compareTo(requestedAmount) < 0) {
            throw InsufficientFundsException.of(INSUFFICIENT_BALANCE_MESSAGE, balance, requestedAmount);
        }
    }
}