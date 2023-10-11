package com.truenorth.truenorth.controller;

import com.truenorth.truenorth.controller.dto.request.CreateNewUserRequest;
import com.truenorth.truenorth.controller.dto.response.UserResponse;
import com.truenorth.truenorth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
@Tag(name = "CourseController", description = "Controller to manage the users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("sign-in")
    @Operation(summary = "Create User", description = "This method creates a new user")
    public ResponseEntity<UserResponse> createCourse(@RequestBody CreateNewUserRequest createNewUserRequest){
        UserResponse userResponse = userService.createNewUser(createNewUserRequest);
        return ResponseEntity.ok(userResponse);
    }

}
