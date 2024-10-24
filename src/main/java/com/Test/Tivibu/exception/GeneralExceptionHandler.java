package com.Test.Tivibu.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(IdNotUniqueException.class)
    public ResponseEntity<?> adminNotFoundExceptionHandler(IdNotUniqueException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongParameterException.class)
    public ResponseEntity<?> wrongParameterExceptionHandler(WrongParameterException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotUniqueException.class)
    public ResponseEntity<?> usernameNotUniqueExceptionHandler(UsernameNotUniqueException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);    }

    @ExceptionHandler(FalseTestWithoutCommentException.class)
    public ResponseEntity<?> falseTestWithoutCommentExceptionHandler(FalseTestWithoutCommentException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);    }

    @ExceptionHandler(TestHasNoSubTestsException.class)
    public ResponseEntity<?> testHasNoSubTestsExceptionHandler(TestHasNoSubTestsException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException exception){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);    }



}
