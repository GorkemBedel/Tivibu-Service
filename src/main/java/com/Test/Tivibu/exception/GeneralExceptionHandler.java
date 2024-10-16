package com.Test.Tivibu.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(IdNotUniqueException.class)
    public ResponseEntity<?> adminNotFoundExceptionHandler(IdNotUniqueException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongParameterException.class)
    public ResponseEntity<?> wrongParameterExceptionHandler(WrongParameterException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotUniqueException.class)
    public ResponseEntity<?> usernameNotUniqueExceptionHandler(UsernameNotUniqueException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FalseTestWithoutCommentException.class)
    public ResponseEntity<?> falseTestWithoutCommentExceptionHandler(FalseTestWithoutCommentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
