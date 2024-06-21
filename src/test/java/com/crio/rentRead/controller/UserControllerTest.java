package com.crio.rentRead.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exchanges.LoginUserRequest;
import com.crio.rentRead.exchanges.RegisterUserRequest;
import com.crio.rentRead.services.IUserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Test
    public void testRegisterUser() throws Exception {
        String requestBody = "{\"firstName\": \"Nikita\",\"lastName\": \"Goyal\",\"email\": \"nikitacrio@gmail.com\",\"password\": \"niki123\",\"role\": \"ADMIN\"}";

        User user = new User();
        user.setId(1);
        user.setFirstName("Nikita");
        user.setLastName("Goyal");
        user.setEmail("nikitacrio@gmail.com");
        user.setPassword("niki123");
        user.setRole("ADMIN");
        user.setRentedBooks(new HashSet<>());

        when(userService.registerUser(any(RegisterUserRequest.class))).thenReturn(user);

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Nikita"))
                .andExpect(jsonPath("$.lastName").value("Goyal"))
                .andExpect(jsonPath("$.email").value("nikitacrio@gmail.com"))
                .andExpect(jsonPath("$.password").value("niki123"))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        verify(userService, times(1)).registerUser(any(RegisterUserRequest.class));
    }

    @Test
    public void testLoginUser() throws Exception {
        String requestBody = "{\"email\": \"nikitacrio@gmail.com\",\"password\": \"niki123\"}";
        String response = "Login Successful";

        when(userService.loginUser(any(LoginUserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(response));

        verify(userService, times(1)).loginUser(any(LoginUserRequest.class));
    }
}
