package com.truenorth.truenorth.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateNewUserRequest {
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
}
