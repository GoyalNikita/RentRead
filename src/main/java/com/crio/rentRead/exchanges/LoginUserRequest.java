package com.crio.rentRead.exchanges;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
    
}
