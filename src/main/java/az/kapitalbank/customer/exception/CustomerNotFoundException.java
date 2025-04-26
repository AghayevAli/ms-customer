package az.kapitalbank.customer.exception;

import static az.kapitalbank.customer.exception.constraint.ErrorCode.CUSTOMER_NOT_FOUND;

import java.text.MessageFormat;

public final class CustomerNotFoundException extends CommonException {

    private CustomerNotFoundException(String message) {
        super(CUSTOMER_NOT_FOUND, message);
    }

    public static CustomerNotFoundException of(String message, Object... args) {
        return new CustomerNotFoundException(MessageFormat.format(message, args));
    }
}
