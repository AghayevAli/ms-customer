package az.kapitalbank.customer.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import az.kapitalbank.customer.exception.model.CommonErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class CommonErrorHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        Optional<ConstraintViolation<?>> violation = ex.getConstraintViolations().stream().findAny();
        String errorMessage;
        String errorCode = "parameter_invalid";

        if (violation.isPresent()) {
            errorMessage = violation.get().getMessage();
        } else {
            errorMessage = ex.getMessage();
        }

        addErrorLog(errorCode, errorMessage);

        return new CommonErrorResponse(
                errorCode,
                errorMessage);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("Invalid input");

        String errorCode = "parameter_invalid";

        log.error("Error Code: {}, Error Message: {}, Exception: {}", errorCode, errorMessage,
                "MethodArgumentNotValidException");

        return new CommonErrorResponse(errorCode, errorMessage);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public CommonErrorResponse handleCustomerNotFound(CustomerNotFoundException ex) {
        addErrorLog(ex.getErrorCode(), ex.getMessage());
        return new CommonErrorResponse(
                ex.getErrorCode(),
                ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(InsufficientFundsException.class)
    public CommonErrorResponse handleInsufficientFunds(InsufficientFundsException ex) {
        addErrorLog(ex.getErrorCode(), ex.getMessage());
        return new CommonErrorResponse(
                ex.getErrorCode(),
                ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public CommonErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorCode = "duplicate_key_violation";

        String message = Optional.ofNullable(ex.getRootCause())
                .map(Throwable::getMessage)
                .orElse(ex.getMessage());

        return new CommonErrorResponse(errorCode, message);
    }

    private static void addErrorLog(String errorCode, String errorMessage) {
        log.error("Error Code: {}, Error Message: {}", errorCode, errorMessage);
    }
}
