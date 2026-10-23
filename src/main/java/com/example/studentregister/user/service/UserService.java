package com.example.studentregister.user.service;

import com.example.studentregister.config.PrimitiveResponse;
import com.example.studentregister.user.controller.model.ChangePasswordModel;
import com.example.studentregister.user.controller.model.UserCreateModel;
import com.example.studentregister.user.controller.model.UserModel;
import com.example.studentregister.user.controller.model.UserOutPutModel;
import org.springframework.stereotype.Service;

@Service

public interface UserService {
    UserOutPutModel checkRole(UserModel userModel) throws Exception;
    UserCreateModel create(UserCreateModel userModel) throws Exception;
    PrimitiveResponse changePassword(ChangePasswordModel changePasswordModel) throws Exception;
}
