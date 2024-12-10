package com.janmejay.account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value =  HttpStatus.NOT_FOUND)
public class ResourceNotFoundException  extends RuntimeException{

    public ResourceNotFoundException(String Entity, String fieldName, String value){
        super(String.format("%s is not found with %s :%s", fieldName,Entity,value ));
    }
}
