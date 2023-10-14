package com.truenorth.truenorth.infraestructure.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateUserRequest {
    @JsonProperty
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Schema(allowableValues = {"hector.ocampo@truenorth.com"})
    private String username;
    @JsonProperty
    @NotEmpty
    @Size(min = 6, max = 50)
    @Schema(allowableValues = {"123456789"})
    private String password;
}
