package com.truenorth.truenorth.infraestructure.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.truenorth.truenorth.domain.model.enums.Role;
import com.truenorth.truenorth.domain.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserResponse {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String username;
    @JsonProperty
    private Status status;
    @JsonProperty
    private Role role;
    @JsonProperty
    private BigDecimal balance;
}
