package com.hireportal.demo.exceptions;

import com.hireportal.demo.utilities.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGeneralException(Exception ex) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        baseResponse.setMessages("An error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleCategoryNotFound(CategoryNotFoundException ex) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.NOT_FOUND.value());
        baseResponse.setMessages(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
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
    public ResponseEntity<BaseResponse<String>> handleInvalidCredentials(InvalidCredentialsException ex) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        baseResponse.setMessages(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
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
    public ResponseEntity<BaseResponse<List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BaseResponse<List<String>> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponse.setMessages("Validation failed");
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        baseResponse.setData(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
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