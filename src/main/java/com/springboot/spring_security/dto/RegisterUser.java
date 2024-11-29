package com.springboot.spring_security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUser {
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank
    @Email(message = "email format is not correct")
    private String email;
    @NotBlank(message = "username cannot be blank")
    @Size(min=5, max = 30, message = "the size of the password must be between 5 and 30")
    private String password;
    
}
