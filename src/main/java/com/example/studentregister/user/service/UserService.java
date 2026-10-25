package com.example.studentregister.user.service;

import com.example.studentregister.config.PrimitiveResponse;
import com.example.studentregister.search.CustomComboFilterModel;
import com.example.studentregister.search.filter.CustomComboModel;
import com.example.studentregister.search.filter.SearchFilterModel;
import com.example.studentregister.user.controller.model.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service

public interface UserService {
    UserOutPutModel checkRole(String nationalCode) throws Exception;
    UserCreateModel create(UserCreateModel userModel) throws Exception;
    PrimitiveResponse changePassword(ChangePasswordModel changePasswordModel) throws Exception;

    Page<UserModel> userListGrid(SearchFilterModel filterModel) throws Exception;

    CustomComboModel genderCustomCombo(CustomComboFilterModel model) throws Exception;

     FindNameModel findNameByLogin(String username) throws Exception;
}
