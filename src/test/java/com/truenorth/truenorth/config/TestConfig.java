package com.truenorth.truenorth.config;

import com.truenorth.truenorth.application.security.JwtUtils;
import com.truenorth.truenorth.domain.mapper.MapperUtils;
import com.truenorth.truenorth.domain.service.AuthService;
import com.truenorth.truenorth.domain.service.OperationService;
import com.truenorth.truenorth.domain.service.RecordService;
import com.truenorth.truenorth.domain.service.UserService;
import com.truenorth.truenorth.domain.service.impl.AuthServiceImpl;
import com.truenorth.truenorth.domain.service.impl.OperationServiceImpl;
import com.truenorth.truenorth.domain.service.impl.RecordServiceImpl;
import com.truenorth.truenorth.domain.service.impl.UserServiceImpl;
import com.truenorth.truenorth.domain.validator.Validator;
import com.truenorth.truenorth.infraestructure.api.consumer.RandomStringGenerator;
import com.truenorth.truenorth.infraestructure.repository.OperationRepository;
import com.truenorth.truenorth.infraestructure.repository.RecordRepository;
import com.truenorth.truenorth.infraestructure.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {
    @Bean
    UserRepository repositoryUser() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    MapperUtils mapperUtils() {
        return new MapperUtils();
    }

    @Bean
    UserService serviceUser() {
        return new UserServiceImpl();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return Mockito.spy(PasswordEncoder.class);
    }

    @Bean
    JwtUtils jwtUtils() {
        return Mockito.spy(JwtUtils.class);
    }

    @Bean
    AuthService authService() {
        return new AuthServiceImpl();
    }

    @Bean
    OperationRepository operationRepository() {
        return Mockito.mock(OperationRepository.class);
    }

    @Bean
    RecordRepository recordRepository() {
        return Mockito.mock(RecordRepository.class);
    }

    @Bean
    Validator validator() {
        return Mockito.spy(Validator.class);
    }

    @Bean
    RandomStringGenerator randomStringGenerator() {
        return Mockito.mock(RandomStringGenerator.class);
    }

    @Bean
    OperationService operationService() {
        return new OperationServiceImpl();
    }
    @Bean
    public RestTemplate restTemplate() {
        return Mockito.mock(RestTemplate.class);
    }

    @Bean
    RecordService recordServiceImpl() {
        return new RecordServiceImpl();
    }
}
