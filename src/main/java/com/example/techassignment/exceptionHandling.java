package com.example.techassignment;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;

@ControllerAdvice
public class exceptionHandling{
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParam(MissingServletRequestParameterException exception) {
        String parameterName = exception.getParameterName();
        return new ResponseEntity<Object>("<html><header><title>Error</title></header><body><h1>Error " + HttpStatus.BAD_REQUEST + "</h1><hr><p> \"" + parameterName + "\" is missing.<p><a href=\"http://localhost:8080\">Go Back</a></body></html>", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleWrongParam(NumberFormatException exception){
        String className=exception.getClass().toString();
        return new ResponseEntity<Object>("<html><header><title>Error</title></header><body><h1>Error "+HttpStatus.BAD_REQUEST+"</h1><hr><p>You have entered non-numerical inputs into a number only input.</p><a href=\"http://localhost:8080\">Go Back</a></body></html>",new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity connectionExceptionHandling(ConnectException exception) {
        return new ResponseEntity<Object>("<html><header><title>Error</title></header><body><h1>Error " + HttpStatus.BAD_GATEWAY + "</h1><hr><p>Please check your internet connection.</p><a href=\"http://localhost:8080\">Go Back</a></body></html>", new HttpHeaders(), HttpStatus.BAD_GATEWAY);
    }
}