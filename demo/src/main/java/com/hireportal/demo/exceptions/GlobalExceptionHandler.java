package com.hireportal.demo.exceptions;

import com.hireportal.demo.utilities.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGeneralException(Exception ex, WebRequest request) {
        ex.printStackTrace();

        BaseResponse<String> response = new BaseResponse<>();
        response.setMessages("An unexpected error occurred");
        response.setData(null);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setMessages(ex.getMessage());
        response.setData(null);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFoundException(NotFoundException ex, WebRequest request) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setMessages(ex.getMessage());
        response.setData(null);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<BaseResponse<Object>> handleInvalidCredentialsException(InvalidCredentialsException e) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setMessages(e.getMessage());
        response.setData(null);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApplicationAlreadyExistsException.class)
    public ResponseEntity<BaseResponse<Object>> handleApplicationAlreadyExistsException(ApplicationAlreadyExistsException ex) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setMessages(ex.getMessage());
        response.setData(null);
        response.setStatus(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        BaseResponse<Map<String, String>> response = new BaseResponse<>();
        response.setMessages("Validation errors occurred");
        response.setData(errors);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<BaseResponse<Object>> handleInvalidEmailException(InvalidEmailException ex) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setMessages(ex.getMessage());
        response.setData(null);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<BaseResponse<Object>> handleInternalServerErrorException(InternalServerErrorException ex) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setMessages("An internal error occurred");
        response.setData(null);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}