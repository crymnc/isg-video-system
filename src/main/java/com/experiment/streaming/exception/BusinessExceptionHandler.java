package com.experiment.streaming.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class BusinessExceptionHandler {
    private static final Logger logger = LogManager.getLogger(BusinessExceptionHandler.class);

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity throwBusinessException(BusinessException businessException) {
        logger.error(businessException);
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(businessException.getMessage())
                .debugMessage(businessException.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity throwUnexpectedException(Exception exception) {
        logger.error(exception);
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .debugMessage(exception.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity throwMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.error(exception);
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage(exception.getLocalizedMessage())
                .timestamp(LocalDateTime.now()).build();
        apiError.setSubErrors(new ArrayList<>());
        exception.getAllErrors().forEach(objectError -> {
            ApiError subApiError = ApiError.builder()
                    .message(objectError.getDefaultMessage()).build();
            apiError.getSubErrors().add(subApiError);
        });

        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }
}
