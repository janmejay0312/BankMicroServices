package com.janmejay.account.exceptions;

import com.janmejay.account.dto.ErrorResponseDto;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> validationErrorMessage = new HashMap<>();

        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            validationErrorMessage.put(fieldName, message);
        });
        return new ResponseEntity<>(validationErrorMessage,HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handlerCustomerAlreadyExistException(CustomerAlreadyExistException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.BAD_REQUEST,exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotExistException.class)
    public ResponseEntity<ErrorResponseDto> handlerCustomerNotExistException(CustomerNotExistException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND,exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotExistException.class)
    public ResponseEntity<ErrorResponseDto> handlerAccountAlreadyExistException(AccountNotExistException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND,exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND,exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handlerResourceNotFoundException(Exception exception, WebRequest webRequest){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
