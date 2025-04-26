package az.kapitalbank.customer.exception;

import static az.kapitalbank.customer.exception.constraint.ErrorCode.INSUFFICIENT_BALANCE;

import java.text.MessageFormat;

public final class InsufficientFundsException extends CommonException {

    private InsufficientFundsException(String message) {
        super(INSUFFICIENT_BALANCE, message);
    }

    public static InsufficientFundsException of(String message, Object... args) {
        return new InsufficientFundsException(MessageFormat.format(message, args));
    }
}
