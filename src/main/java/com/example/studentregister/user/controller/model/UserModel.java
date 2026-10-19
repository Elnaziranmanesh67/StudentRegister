package com.example.studentregister.user.controller.model;


import com.example.studentregister.user.orm.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {
    @Size(min = 8, max = 15)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private RoleEnum role;
    private Integer id;
    @NotBlank
    @Size(min = 10, max = 10)
    @Pattern(regexp = "\\d{10}")
    private String nationalCode;

}
