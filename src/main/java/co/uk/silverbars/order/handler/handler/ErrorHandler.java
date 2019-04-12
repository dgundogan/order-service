package co.uk.silverbars.order.handler.handler;

import co.uk.silverbars.order.handler.response.ErrorItem;
import co.uk.silverbars.order.handler.response.ErrorResponse;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException e) {
        ErrorResponse errors = new ErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            ErrorItem error = new ErrorItem();
            error.setCode(violation.getMessageTemplate());
            error.setMessage(violation.getMessage());
            errors.setErrorItem(error);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ErrorResponse> handle(ConversionFailedException e) {
        ErrorResponse errors = new ErrorResponse();
        ErrorItem error = new ErrorItem();
        error.setCode(HttpStatus.BAD_REQUEST.toString());
        error.setMessage("Order Type is wrong value. Acceptable values : [BUY,SELL]");
        errors.setErrorItem(error);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
