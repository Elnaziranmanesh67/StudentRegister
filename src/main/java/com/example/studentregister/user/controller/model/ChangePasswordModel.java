package com.example.studentregister.user.controller.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordModel {
    private  String repeatNewPassword;
    private  String newPassword;
    private String username;
}
