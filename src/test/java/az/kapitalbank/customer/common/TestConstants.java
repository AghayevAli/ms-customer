package az.kapitalbank.customer.common;

import az.kapitalbank.customer.dao.entity.CustomerEntity;
import az.kapitalbank.customer.model.dto.CustomerCreateRequestDto;
import az.kapitalbank.customer.model.dto.CustomerCreateResponseDto;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface TestConstants {

    String BASE_PATH = "/api/v1/customers";
    Long CUSTOMER_ID = 1L;

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
}