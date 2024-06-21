package com.crio.rentRead.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exceptions.InvalidCredentialsException;
import com.crio.rentRead.exceptions.UserNotFoundException;
import com.crio.rentRead.exceptions.UserNotRegisteredException;
import com.crio.rentRead.exchanges.LoginUserRequest;
import com.crio.rentRead.exchanges.RegisterUserRequest;
import com.crio.rentRead.repositoryServices.IUserRepositoryService;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepositoryService userRepositoryService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerUser(RegisterUserRequest registerUserRequest) {
        String firstName = registerUserRequest.getFirstName();
        String lastName = registerUserRequest.getLastName();
        String email = registerUserRequest.getEmail();
        String password = registerUserRequest.getPassword();
        String role = registerUserRequest.getRole().toUpperCase();

        User user = userRepositoryService.registerUser(firstName, lastName, email, password, role);
        return user;
    }

    @Override
    public String loginUser(LoginUserRequest loginUserRequest)
            throws InvalidCredentialsException, UserNotRegisteredException {
        String email = loginUserRequest.getEmail();
        String password = loginUserRequest.getPassword();
        User user;

        try {
            user = userRepositoryService.getUserByEmail(email);
        } catch (UserNotFoundException e) {
            throw new UserNotRegisteredException("This user is not registered");
        }

        if (isPasswordMatching(password, user.getPassword())) {
            String response = "Login successful!";
            return response;
        } else
            throw new InvalidCredentialsException("Invalid email or password!");
    }

    private boolean isPasswordMatching(String actualPassword, String expectedPassword) {
        return bCryptPasswordEncoder.matches(actualPassword, expectedPassword);
    }

}
