package az.kapitalbank.customer.controller;

import az.kapitalbank.customer.model.dto.BalanceChangeRequestDto;
import az.kapitalbank.customer.service.InternalBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/customers/{customerId}/balance")
@RequiredArgsConstructor
public class InternalBalanceController {

    private final InternalBalanceService balanceService;


    @PostMapping("/increase")
    public ResponseEntity<Void> increaseBalance(@PathVariable Long customerId,
                                                @RequestBody BalanceChangeRequestDto request) {
        balanceService.increaseBalance(customerId, request);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/decrease")
    public ResponseEntity<Void> decreaseBalance(@PathVariable Long customerId,
                                                @RequestBody BalanceChangeRequestDto request) {
        balanceService.decreaseBalance(customerId, request);

        return ResponseEntity.noContent().build();
    }

}
