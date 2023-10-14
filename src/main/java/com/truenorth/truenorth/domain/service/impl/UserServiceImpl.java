package com.truenorth.truenorth.domain.service.impl;

import com.truenorth.truenorth.application.exception.ErrorCode;
import com.truenorth.truenorth.application.exception.ExceptionNullValue;
import com.truenorth.truenorth.domain.mapper.MapperUtils;
import com.truenorth.truenorth.domain.model.AuthUserDetails;
import com.truenorth.truenorth.domain.model.User;
import com.truenorth.truenorth.domain.service.UserService;
import com.truenorth.truenorth.infraestructure.controller.dto.response.UserResponse;
import com.truenorth.truenorth.infraestructure.repository.RecordRepository;
import com.truenorth.truenorth.infraestructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MapperUtils mapperUtils;

    @Override
    public UserResponse findUserById(Long id) throws ExceptionNullValue {
        User user = userRepository.findById(id).orElseThrow(()-> new ExceptionNullValue(ErrorCode.EC0003.message));
        return mapperUtils.convertUserToUserReponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws ExceptionNullValue {
        User user = userRepository.findByUsername(username).orElseThrow( ()-> new ExceptionNullValue(ErrorCode.EC0003.message));
        return AuthUserDetails.build(user);
    }

}
