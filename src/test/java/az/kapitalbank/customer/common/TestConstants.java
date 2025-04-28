package az.kapitalbank.customer.common;

import az.kapitalbank.customer.dao.entity.CustomerEntity;
import az.kapitalbank.customer.model.dto.BalanceChangeRequestDto;
import az.kapitalbank.customer.model.dto.CustomerCreateRequestDto;
import az.kapitalbank.customer.model.dto.CustomerCreateResponseDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface TestConstants {

    String BASE_PATH = "/api/v1/customers";
    String INTERNAL_BASE_PATH = "/api/v1/internal/customers";
    Long CUSTOMER_ID = 1L;
    UUID TRANSACTION_ID = UUID.fromString("fa213214-92ea-4c54-820c-81dece9bb318");

    CustomerCreateRequestDto VALID_CUSTOMER_REQUEST = CustomerCreateRequestDto.builder()
            .name("John")
            .surname("Doe")
            .phoneNumber("+994501234567")
            .birthDate(LocalDate.of(1990, 1, 1))
            .build();

    CustomerCreateResponseDto VALID_CUSTOMER_RESPONSE = CustomerCreateResponseDto.builder()
            .customerId(CUSTOMER_ID)
            .name("John")
            .surname("Doe")
            .phoneNumber("+994501234567")
            .birthDate(LocalDate.of(1990, 1, 1))
            .createdAt(LocalDate.now())
            .build();

    CustomerCreateRequestDto INVALID_CUSTOMER_REQUEST = CustomerCreateRequestDto.builder()
            .name("")
            .surname("")
            .phoneNumber("invalid-phone")
            .birthDate(LocalDate.now().plusDays(1))
            .build();

    CustomerEntity CUSTOMER_ENTITY = CustomerEntity.builder()
            .name("John")
            .surname("Doe")
            .phoneNumber("+994501234567")
            .birthDate(LocalDate.of(1990, 1, 1))
            .balance(BigDecimal.valueOf(100))
            .build();

    CustomerEntity CUSTOMER_ENTITY_WITH_BALANCE_200 = CustomerEntity.builder()
            .name("John")
            .surname("Doe")
            .phoneNumber("+994501234567")
            .birthDate(LocalDate.of(1990, 1, 1))
            .balance(BigDecimal.valueOf(200))
            .build();

    CustomerEntity CUSTOMER_ENTITY_WITH_BALANCE_300 = CustomerEntity.builder()
            .name("John")
            .surname("Doe")
            .phoneNumber("+994501234567")
            .birthDate(LocalDate.of(1990, 1, 1))
            .balance(BigDecimal.valueOf(300))
            .build();


    BalanceChangeRequestDto VALID_BALANCE_CHANGE_REQUEST = BalanceChangeRequestDto.builder()
            .transactionId(TRANSACTION_ID)
            .amount(BigDecimal.valueOf(50))
            .build();
}