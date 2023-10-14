package com.truenorth.truenorth.domain.service;


import com.truenorth.truenorth.application.exception.ErrorCode;
import com.truenorth.truenorth.application.exception.ExceptionInvalidValue;
import com.truenorth.truenorth.application.exception.ExceptionNullValue;
import com.truenorth.truenorth.application.security.JwtUtils;
import com.truenorth.truenorth.config.TestConfig;
import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.domain.model.enums.Role;
import com.truenorth.truenorth.domain.model.enums.Status;
import com.truenorth.truenorth.infraestructure.controller.dto.request.CreateNewUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.request.LoginRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.request.UpdateUserRequest;
import com.truenorth.truenorth.infraestructure.controller.dto.response.JwtResponse;
import com.truenorth.truenorth.infraestructure.controller.dto.response.MessageResponse;
import com.truenorth.truenorth.infraestructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.truenorth.truenorth.application.constants.ConstantsMessages.USER_CREATED;
import static com.truenorth.truenorth.application.constants.ConstantsMessages.USER_UPDATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"app.jwtExpirationMs=123", "app.jwtSecret=1233213123123124124fcsdfsdfsdfsdfsd"})
public class AuthServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthService authService;

    @Test
    public void testAuthenticateUser() {
        when(authenticationManager.authenticate(any())).thenReturn(authentication());
        JwtResponse jwtResponse = authService.authenticateUser(new LoginRequest("email", "password"));
        Assertions.assertNotNull( jwtResponse.getToken());
    }

    public Authentication authentication() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        return new UsernamePasswordAuthenticationToken(userDetails, "customPassword", authorities);
    }

    @Test
    public void testRegisterUser() {
        when(userRepository.existsByUsername("username")).thenReturn(false);
        when(encoder.encode("password")).thenReturn("encoded_password");
        MessageResponse messageResponse = authService.registerUser(new CreateNewUserRequest("username", "password"));
        Assertions.assertEquals(USER_CREATED, messageResponse.getMessage());
    }

    @Test
    public void testRegisterUser_UsernameAlreadyExists() {
        when(userRepository.existsByUsername("username")).thenReturn(true);
        try {
            authService.registerUser(new CreateNewUserRequest("username", "password"));
        } catch (ExceptionInvalidValue e) {
            Assertions.assertEquals(ErrorCode.EC0001.message, e.getMessage());
        }
    }

    @Test
    public void testUpdateUser() throws ExceptionNullValue {
        User user = new User();
        user.setRole(Role.ROLE_USER);
        user.setUsername("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        AuthUserDetails principal = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        MessageResponse messageResponse = authService.updateUser(new UpdateUserRequest("username", "password"),principal);
        Assertions.assertEquals(USER_UPDATED, messageResponse.getMessage());
    }

    @Test
    public void testUpdateUser_UserNotFound() throws ExceptionNullValue {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        AuthUserDetails principal = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        try {
            authService.updateUser(new UpdateUserRequest("username", "password"), principal);
        } catch (ExceptionNullValue e) {
            Assertions.assertEquals(ErrorCode.EC0003.message, e.getMessage());
        }
    }

    @Test
    public void testUpdateUser_NotAuthorized() throws ExceptionNullValue {
        User user = new User();
        user.setRole(Role.ROLE_USER);
        user.setUsername("username");
        AuthUserDetails principal = new AuthUserDetails(1L, "username", "encoded_password", Status.ACTIVE, Role.ROLE_USER , null);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        try {
            authService.updateUser(new UpdateUserRequest("username", "password"), principal);
        } catch (AccessDeniedException e) {
            Assertions.assertEquals(ErrorCode.EC0002.message, e.getMessage());
        }
    }

}
