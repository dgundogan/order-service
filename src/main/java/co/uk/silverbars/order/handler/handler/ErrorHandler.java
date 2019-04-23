package co.uk.silverbars.order.handler.handler;

import co.uk.silverbars.order.handler.response.ErrorItem;
import co.uk.silverbars.order.handler.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ErrorResponse> handle(ConversionFailedException e) {
        ErrorResponse errors = new ErrorResponse();
        ErrorItem error = new ErrorItem();
        error.setCode(HttpStatus.BAD_REQUEST.toString());
        error.setMessage("Order Type is wrong value. Acceptable values : [BUY,SELL]");
        errors.setErrorItem(error);
        log.error("Error:",errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        ErrorResponse errors = new ErrorResponse();
        ErrorItem error = new ErrorItem();
        error.setCode(HttpStatus.BAD_REQUEST.toString());
        error.setMessage("General Exception");
        errors.setErrorItem(error);
        log.error("Error:",errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
