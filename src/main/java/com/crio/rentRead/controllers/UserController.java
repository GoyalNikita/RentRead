package com.crio.rentRead.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exceptions.InvalidCredentialsException;
import com.crio.rentRead.exceptions.UserNotRegisteredException;
import com.crio.rentRead.exchanges.LoginUserRequest;
import com.crio.rentRead.exchanges.RegisterUserRequest;
import com.crio.rentRead.services.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(UserController.USER_API_ENDPOINT)
public class UserController {
    public static final String USER_API_ENDPOINT = "/users";

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        User registeredUser = userService.registerUser(registerUserRequest);
        return ResponseEntity.ok().body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginUserRequest loginUserRequest)
            throws InvalidCredentialsException, UserNotRegisteredException {
        String response = userService.loginUser(loginUserRequest);
        return ResponseEntity.ok().body(response);
    }
}
