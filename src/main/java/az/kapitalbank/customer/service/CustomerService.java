package az.kapitalbank.customer.service;

import az.kapitalbank.customer.dao.entity.CustomerEntity;
import az.kapitalbank.customer.dao.repository.CustomerRepository;
import az.kapitalbank.customer.mapper.CustomerMapper;
import az.kapitalbank.customer.model.dto.CustomerCreateRequestDto;
import az.kapitalbank.customer.model.dto.CustomerCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    final CustomerRepository customerRepository;

    public CustomerCreateResponseDto createCustomer(CustomerCreateRequestDto request) {
        log.info("Received request to create customer: {}", request);

        CustomerEntity customerEntity = CustomerMapper.INSTANCE.toCustomerEntity(request);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        log.info("Customer saved with ID: {}", savedCustomer.getId());

        return CustomerMapper.INSTANCE.toCustomerCreateResponseDto(savedCustomer);
    }

}
