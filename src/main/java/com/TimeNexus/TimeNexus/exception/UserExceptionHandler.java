package com.TimeNexus.TimeNexus.exception;

import com.TimeNexus.TimeNexus.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to handle all exceptions generated in the APIs.
 */
@Slf4j
@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<String> messages = new ArrayList<>();
        errors.forEach(fieldError -> {
            log.error("Invalid {} value submitted for {}", fieldError.getRejectedValue(), fieldError.getField());
            messages.add(fieldError.getField() + " cannot be null/empty.");
        });
        ErrorResponse errorResponse = new ErrorResponse(messages);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to handle UserNotFoundException. Will be thrown when request tries
     * to query for a use that does not exist.
     * @param ex Exception thrown
     * @return Customized error response
     */
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Method to handle any generic exception thrown.
     * @param ex Exception thrown
     * @return Customized error response
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGenericException(Exception ex){
        log.error("Some error occurred: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(Collections.singletonList("Something Went wrong!"));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
