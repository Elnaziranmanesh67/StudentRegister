package com.example.studentregister.loggin.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "نام کاربری الزامی است")
    private String username;
    @NotBlank(message = "رمز عبور الزامی است")
    private String password;
}

