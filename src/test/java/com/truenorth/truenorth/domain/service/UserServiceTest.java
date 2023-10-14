package com.truenorth.truenorth.domain.service;

import com.truenorth.truenorth.application.exception.ErrorCode;
import com.truenorth.truenorth.application.exception.ExceptionNullValue;
import com.truenorth.truenorth.config.TestConfig;
import com.truenorth.truenorth.domain.model.Record;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.domain.model.enums.Role;
import com.truenorth.truenorth.infraestructure.controller.dto.response.UserResponse;
import com.truenorth.truenorth.infraestructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"app.jwtExpirationMs=123", "app.jwtSecret=1233213123123124124fcsdfsdfsdfsdfsd"})
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testFindUserById() throws Exception {
        User user = new User();
        user.setRole(Role.ROLE_USER);
        Record record = new Record();
        record.setUserBalance(BigDecimal.valueOf(100));
        user.setRecordsById(List.of(record));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse userResponse = userService.findUserById(1L);
        Assertions.assertNotNull(userResponse);
    }

    @Test
    public void testFindUserById_UserNotFound() throws ExceptionNullValue {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            userService.findUserById(1L);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), ErrorCode.EC0003.message);
        }
    }

    @Test
    public void testLoadUserByUsername() throws ExceptionNullValue {
        User user = new User();
        user.setRole(Role.ROLE_USER);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("username");
        Assertions.assertNotNull(userDetails);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() throws ExceptionNullValue {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        try {
            userService.loadUserByUsername("username");
        } catch (ExceptionNullValue e) {
            Assertions.assertEquals(e.getMessage(), ErrorCode.EC0003.message);
        }
    }
}