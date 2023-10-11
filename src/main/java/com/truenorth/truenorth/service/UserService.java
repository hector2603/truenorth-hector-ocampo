package com.truenorth.truenorth.service;

import com.truenorth.truenorth.controller.dto.request.CreateNewUserRequest;
import com.truenorth.truenorth.controller.dto.response.UserResponse;

public interface UserService {

    UserResponse createNewUser(CreateNewUserRequest createNewUser);

}
