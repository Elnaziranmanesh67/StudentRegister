package com.example.studentregister.user.service;

import com.example.studentregister.config.PrimitiveResponse;
import com.example.studentregister.search.CustomComboFilterModel;
import com.example.studentregister.search.filter.CustomComboModel;
import com.example.studentregister.search.filter.SearchFilterModel;
import com.example.studentregister.user.controller.model.ChangePasswordModel;
import com.example.studentregister.user.controller.model.UserCreateModel;
import com.example.studentregister.user.controller.model.UserModel;
import com.example.studentregister.user.controller.model.UserOutPutModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service

public interface UserService {
    UserOutPutModel checkRole(UserModel userModel) throws Exception;
    UserCreateModel create(UserCreateModel userModel) throws Exception;
    Page<UserModel> userListGrid(SearchFilterModel filterModel) throws Exception;
    PrimitiveResponse changePassword(ChangePasswordModel changePasswordModel) throws Exception;
    CustomComboModel customCombo(CustomComboFilterModel model) throws Exception;
}
