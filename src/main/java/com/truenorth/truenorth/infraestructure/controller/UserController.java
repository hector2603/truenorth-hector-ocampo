package com.truenorth.truenorth.infraestructure.controller;

import com.truenorth.truenorth.application.exception.ExceptionNullValue;
import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.domain.service.AuthService;
import com.truenorth.truenorth.domain.service.UserService;
import com.truenorth.truenorth.infraestructure.controller.dto.request.UpdateUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.UserResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.WrapperResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.truenorth.truenorth.application.constants.PathConstants.USER;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://truenorth-hector-ocampo-frontent-calculator.vercel.app/"}, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(USER)
@Tag(name = "UserController", description = "Controller to manage the users")
public class UserController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @PatchMapping()
    @Operation(summary = "This method is used to update the password for a user", description = "if you are a user you can update your personal information, if you are admin you can updater the information for any user")
    public ResponseEntity<MessageResponse> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, Authentication authentication) throws ExceptionNullValue {
        MessageResponse messageResponse = authService.updateUser(updateUserRequest, (AuthUserDetails)authentication.getPrincipal());
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping
    @Operation(summary = "this method is used to fecth the information of the current user", description = "this method takes the JWT and fetch the information of this person")
    public ResponseEntity<WrapperResponse<UserResponse>> getUSer(Authentication authentication) throws Exception {
        UserResponse userResponse = userService.findUserById(((AuthUserDetails)authentication.getPrincipal()).getId());
        return ResponseEntity.ok(new WrapperResponse<>(userResponse));
    }
}
