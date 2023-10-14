package com.truenorth.truenorth.infraestructure.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {

    private String message;
    private ErrorResponse error;

    public MessageResponse(String message){
        this.message = message;
    }
}
