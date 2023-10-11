package com.truenorth.truenorth.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.truenorth.truenorth.model.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String username;
    @JsonProperty
    private Status status;
}
