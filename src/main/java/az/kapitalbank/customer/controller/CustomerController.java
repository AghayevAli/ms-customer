package az.kapitalbank.customer.controller;

import az.kapitalbank.customer.model.dto.CustomerCreateRequestDto;
import az.kapitalbank.customer.model.dto.CustomerCreateResponseDto;
import az.kapitalbank.customer.service.CustomerService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerCreateResponseDto> createCustomer(
            @Valid @RequestBody CustomerCreateRequestDto request
    ) {
        CustomerCreateResponseDto createdCustomer = customerService.createCustomer(request);

        return ResponseEntity.created(URI.create("/api/v1/customers/" + createdCustomer.getCustomerId()))
                .body(createdCustomer);
    }

}
