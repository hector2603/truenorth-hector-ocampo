package com.truenorth.truenorth.infraestructure.controller.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {

    private String exceptionName;
    private String message;

    public ErrorResponse(String exceptionName, String message) {
        this.exceptionName = exceptionName;
        this.message = message;
    }

}
