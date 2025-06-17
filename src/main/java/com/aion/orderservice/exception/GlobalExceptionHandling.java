package com.aion.orderservice.exception;

import com.aion.orderservice.dto.ApiResponse;
import com.aion.orderservice.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandling {

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApiResponse<ExceptionDetails>>  handleValidationExceptions(MethodArgumentNotValidException exception, WebRequest webRequest) {
       Map<String, String> errors = new HashMap<>();
       exception.getBindingResult().getAllErrors().forEach((error) -> {
           String fieldName = ((FieldError) error).getField();
           String errorMessage = error.getDefaultMessage();
           errors.put(fieldName, errorMessage);
       });
       log.error("MethodArgumentNotValidException : " + exception.getMessage());
       return new ResponseEntity<>(new ApiResponse<>(Constants.STATUS_CODE_FAILURE, Constants.STATUS_FAILURE_MESSAGE,
               new ExceptionDetails(new Date(), errors, webRequest.getDescription(true))), HttpStatus.BAD_REQUEST);
   }
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiResponse<ExceptionDetails>>  handleValidationExceptions(JsonProcessingException exception, WebRequest webRequest) {
        log.error("JsonProcessingException : " + exception.getMessage());
        exception.printStackTrace();
        return new ResponseEntity<>(new ApiResponse<>(Constants.STATUS_CODE_FAILURE, Constants.STATUS_FAILURE_MESSAGE,
                new ExceptionDetails(new Date(), Constants.INVALID_INFORMATION_PROVIDED, webRequest.getDescription(true))), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponse<ExceptionDetails>>  handleValidationExceptions(EmptyResultDataAccessException exception, WebRequest webRequest) {
        log.error("EmptyResultDataAccessException : " + exception.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(Constants.STATUS_CODE_FAILURE, Constants.STATUS_FAILURE_MESSAGE,
                new ExceptionDetails(new Date(), Constants.INVALID_ID, webRequest.getDescription(true))), HttpStatus.NOT_FOUND);
    }
}
