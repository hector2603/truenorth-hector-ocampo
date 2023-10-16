package com.truenorth.truenorth.infraestructure.controller;

import com.truenorth.truenorth.domain.service.AuthService;
import com.truenorth.truenorth.infraestructure.controller.dto.request.CreateNewUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.request.LoginRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.JwtResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.truenorth.truenorth.application.constants.PathConstants.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://truenorth-hector-ocampo-frontent-calculator.vercel.app/"}, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(AUTH)
@Tag(name = "Auth Controller", description = "Controller to manage the users authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(SIGN_UP)
    @Operation(summary = "Create User", description = "This method creates a new user")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody CreateNewUserRequest createNewUserRequest){
        MessageResponse userResponse = authService.registerUser(createNewUserRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(SIGN_IN)
    @Operation(summary = "This method is used for login into the truenorth app", description = "This method is used for login into the truenorth app")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwt = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwt);
    }

}
