package com.example.studentregister.user.controller;

import com.example.studentregister.config.CustomObjectMapper;
import com.example.studentregister.config.PrimitiveResponse;
import com.example.studentregister.search.CustomComboFilterModel;
import com.example.studentregister.search.filter.CustomComboModel;
import com.example.studentregister.search.filter.SearchFilterModel;
import com.example.studentregister.user.controller.model.ChangePasswordModel;
import com.example.studentregister.user.controller.model.UserModel;
import com.example.studentregister.user.controller.model.UserOutPutModel;
import com.example.studentregister.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/user-list-grid")
    public Page<UserModel> userListGrid(@RequestParam(name = "searchFilterModel") Optional<String> filter) throws Exception {
        CustomObjectMapper customObjectMapper = new CustomObjectMapper();
        SearchFilterModel searchFilterModel = customObjectMapper.readValue(filter.get(), SearchFilterModel.class);
        return userService.userListGrid(searchFilterModel);
    }

    @GetMapping(value = "/gender-custom-combo")
    public CustomComboModel CustomCombo(@RequestParam(name = "customComboFilterModel") @Valid Optional< String > customComboFilterModel) throws Exception {
        CustomObjectMapper customObjectMapper = new CustomObjectMapper();
        CustomComboFilterModel filterModel = customObjectMapper.readValue(customComboFilterModel.get(), CustomComboFilterModel.class);
        return userService.genderCustomCombo(filterModel);
    }

    @GetMapping("/check-role/{nationalCode}")
    public UserOutPutModel checkRole(@PathVariable String nationalCode) throws Exception {
        return userService.checkRole(nationalCode);
    }

    @PutMapping("/change-password")
    public PrimitiveResponse changePassword(@RequestBody @Valid ChangePasswordModel changePasswordModel) throws Exception {
        return userService.changePassword(changePasswordModel);
    }

}
