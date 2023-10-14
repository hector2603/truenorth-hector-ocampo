package com.truenorth.truenorth.infraestructure.controller.dto.response;

public class WrapperResponse <T>{
    private T data;

    public WrapperResponse(T data){
        this.data = data;
    }

    public T getData(){
        return data;
    }

}
