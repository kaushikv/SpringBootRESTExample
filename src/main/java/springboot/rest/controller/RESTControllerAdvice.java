package springboot.rest.controller;

import springboot.rest.util.ResourceAlreadyExistsException;
import springboot.rest.util.ResourceValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RESTControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceValidationException.class})
    protected ResponseEntity<Object> handleValidationError(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Error creating resource: " + ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
    protected ResponseEntity<Object> handleDuplicateError(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Error creating resource: " + ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}

