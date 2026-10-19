package com.example.studentregister.loggin.model;

import com.example.studentregister.user.orm.RoleEnum;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String nationalCode;
    private String password;
    private String firstName;
    private String lastName;
    private RoleEnum role;
}
