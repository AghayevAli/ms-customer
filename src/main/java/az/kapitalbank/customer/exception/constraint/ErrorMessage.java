package az.kapitalbank.customer.exception.constraint;

import lombok.NoArgsConstructor;


@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ErrorMessage {

    public static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found with ID [{0}]";

}
