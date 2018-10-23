package com.zzh.demo.error;

public class BusinessException extends RuntimeException {

    public BusinessException(){}

    public BusinessException(String message){
        super(message);
    }
}
