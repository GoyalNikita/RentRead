package com.crio.rentRead.repositoryServices;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exceptions.UserNotFoundException;

public interface IUserRepositoryService {
    
    User registerUser(String firstName, String lastName, String email, String password, String role);

    User saveUser(User user);

    User getUserById(int userId) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;
    
}
