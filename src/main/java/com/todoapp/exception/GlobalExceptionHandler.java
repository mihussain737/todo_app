package com.todoapp.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(Exception ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(new Date());
        errorDetails.setPath(webRequest.getDescription(false));
        errorDetails.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceAlreadyFound.class)
    public ResponseEntity<ErrorDetails> handleResourceAlreadyFound(Exception ex, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(new Date());
        errorDetails.setPath(webRequest.getDescription(false));
        errorDetails.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//        Map<String,String> errors=new HashMap<>();
//        List<ObjectError> errorList=ex.getBindingResult().getAllErrors();
//
//        errorList.forEach(
//                (error)->{
//
//                    String fieldName=((FieldError)error).getField();
//                    String message= error.getDefaultMessage();
//
//                    errors.put(fieldName,message);
//                }
//        );
//        return ResponseEntity.badRequest().body(errors);
//    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatusCode statusCode,WebRequest request){
        Map<String,String> errors=new HashMap<>();
        List<ObjectError> errorList=ex.getBindingResult().getAllErrors();

        errorList.forEach((error)->
        {
            String fieldName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            errors.put(fieldName,message);
        });
        return ResponseEntity.badRequest().body(errors);
    }


}

