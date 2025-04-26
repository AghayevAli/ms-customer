package az.kapitalbank.customer.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateResponseDto {
    private Long customerId;
    private String name;
    private String surname;
    private String phoneNumber;
    private LocalDate birthDate;
    private BigDecimal balance;
    private LocalDate createdAt;
}
