package com.truenorth.truenorth.domain.service.impl;

import com.truenorth.truenorth.application.security.JwtUtils;
import com.truenorth.truenorth.application.exception.ErrorCode;
import com.truenorth.truenorth.application.exception.ExceptionInvalidValue;
import com.truenorth.truenorth.application.exception.ExceptionNullValue;
import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.domain.model.enums.Role;
import com.truenorth.truenorth.domain.model.enums.Status;
import com.truenorth.truenorth.domain.service.AuthService;
import com.truenorth.truenorth.infraestructure.controller.dto.request.CreateNewUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.request.LoginRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.request.UpdateUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.JwtResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.truenorth.truenorth.application.constants.ConstantsMessages.USER_CREATED;
import static com.truenorth.truenorth.application.constants.ConstantsMessages.USER_UPDATED;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    @Override
    public MessageResponse registerUser(CreateNewUserRequest createNewUserRequest) {
        if (userRepository.existsByUsername(createNewUserRequest.getUsername())) {
            throw new ExceptionInvalidValue(ErrorCode.EC0001.message);
        }
        var user = new User();
        user.setPassword(encoder.encode(createNewUserRequest.getPassword()));
        user.setUsername(createNewUserRequest.getUsername());
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return new MessageResponse(USER_CREATED);
    }

    @Override
    public MessageResponse updateUser(UpdateUserRequest updateUserRequest, AuthUserDetails principal) throws ExceptionNullValue {
        if(updateUserRequest.getUsername().equalsIgnoreCase(principal.getUsername()) || principal.getRole() == Role.ROLE_ADMIN){
            User user =userRepository.findByUsername(updateUserRequest.getUsername()).orElseThrow(() -> new ExceptionNullValue(ErrorCode.EC0003.message));
            user.setPassword(encoder.encode(updateUserRequest.getPassword()));
            userRepository.save(user);
            return new MessageResponse(USER_UPDATED);
        }else{
            throw new AccessDeniedException(ErrorCode.EC0002.message);
        }
    }


}
