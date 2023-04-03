package com.example.demowithtests.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ResourcePrivateException.class)
    public ResponseEntity<?> resourcePrivateException(WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "It`s private resources",
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.LOCKED);
    }
    @ExceptionHandler(PassportIsTakenException.class)
    public ResponseEntity<?> passportIsTakenException(PassportIsTakenException ex, WebRequest request){
     ErrorDetails errorDetails = new ErrorDetails(new Date(), "This passport is alreasy taken",
             request.getDescription(false));
     return new ResponseEntity<>(errorDetails, HttpStatus.LOCKED);
    }
@ExceptionHandler(PhotoNotFoundException.class)
public ResponseEntity<?> photoNotFoundException(PhotoNotFoundException ex,WebRequest request){
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));

    return new ResponseEntity<>(errorDetails, HttpStatus.LOCKED);
}
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
     /*   ErrorDetails errorDetails =
                new ErrorDetails(new Date(),
                        "Employee not found with id =" + request.getDescription(true),//getParameter("id"),
                        request.getDescription(false));*/
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<MyGlobalExceptionHandler> handleDeleteException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was deleted"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    private static class MyGlobalExceptionHandler {
        private String message;
    }
}
