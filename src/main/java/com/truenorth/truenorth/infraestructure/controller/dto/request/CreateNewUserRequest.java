package com.truenorth.truenorth.infraestructure.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateNewUserRequest {

    @NotEmpty(message = "username has to be present")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$" , message = "username has to be email")
    @Schema(allowableValues = {"hector.ocampo@truenorth.com"})
    private String username;
    @NotEmpty(message = "password has to be present")
    @Size(min = 6, max = 50 ,   message = "The password '${validatedValue}' must be between {min} and {max} characters long")
    @Schema(allowableValues = {"123456789"})
    private String password;
}
