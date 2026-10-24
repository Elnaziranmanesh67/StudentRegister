package com.example.studentregister.user.controller;

import com.example.studentregister.config.CustomObjectMapper;
import com.example.studentregister.search.CustomComboFilterModel;
import com.example.studentregister.search.filter.CustomComboModel;
import com.example.studentregister.search.filter.SearchFilterModel;
import com.example.studentregister.user.controller.model.UserModel;
import com.example.studentregister.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
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

}
