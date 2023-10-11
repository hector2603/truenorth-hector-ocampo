package com.truenorth.truenorth.service.impl;

import com.truenorth.truenorth.controller.dto.request.CreateNewUserRequest;
import com.truenorth.truenorth.controller.dto.response.UserResponse;
import com.truenorth.truenorth.model.Status;
import com.truenorth.truenorth.model.User;
import com.truenorth.truenorth.repository.UserRepository;
import com.truenorth.truenorth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserResponse createNewUser(CreateNewUserRequest createNewUser) {
        User newUser = userRepository.save(User.builder().userName(createNewUser.getUsername()).password(createNewUser.getPassword()).status(Status.ACTIVE).build());
        return UserResponse.builder().id(newUser.getId()).username(newUser.getUserName()).status(newUser.getStatus()).build();
    }
}
