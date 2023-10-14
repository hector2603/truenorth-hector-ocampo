package com.truenorth.truenorth.domain.service;

import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.infraestructure.controller.dto.request.UpdateUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
    UserResponse findUserById(Long id) throws Exception;

}
