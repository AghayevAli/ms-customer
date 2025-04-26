package az.kapitalbank.customer.exception.constraint;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ErrorCode {

    public static final String CUSTOMER_NOT_FOUND = "customer_not_found";
    public static final String INSUFFICIENT_BALANCE = "insufficient_balance";

}
