package com.truenorth.truenorth.domain.service;

import com.truenorth.truenorth.application.exception.ExceptionNullValue;
import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.infraestructure.controller.dto.request.CreateNewUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.request.LoginRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.request.UpdateUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.JwtResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);

    MessageResponse registerUser(CreateNewUserRequest createNewUserRequest);

    MessageResponse updateUser(UpdateUserRequest updateUserRequest, AuthUserDetails principal) throws ExceptionNullValue;
}
