package com.example.studentregister.user.service;


import com.example.studentregister.config.PrimitiveResponse;
import com.example.studentregister.enumRegister.GenderEnum;
import com.example.studentregister.exception.BusinessException;
import com.example.studentregister.exception.BusinessExceptionKeyImpl;
import com.example.studentregister.search.CustomComboFilterModel;
import com.example.studentregister.search.filter.*;
import com.example.studentregister.user.controller.model.*;
import com.example.studentregister.user.orm.User;
import com.example.studentregister.user.orm.UserRepository;
import com.example.studentregister.user.orm.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Service
@Qualifier("UserServiceImpl")
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserCreateModel create(UserCreateModel userModel) throws Exception {

        User user = userRepository.findByUsernameAndNationalCode(userModel.getUsername(), userModel.getNationalCode());
        if (user != null) {
            throw new BusinessException(BusinessExceptionKeyImpl.USER_NOT_FOUND);
        }
        user = this.convertModelToEntity(userModel);
        return convertEntityToModel(user);
    }

    public User convertModelToEntity(UserCreateModel userModel) {
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setNationalCode(userModel.getNationalCode());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setCreatedAt(new Date());
        userRepository.save(user);
        return user;
    }

    public UserCreateModel convertEntityToModel(User user) {
        UserCreateModel userCreateModel = new UserCreateModel();
        BeanUtils.copyProperties(user, userCreateModel);
        return userCreateModel;
    }

    public PrimitiveResponse changePassword(ChangePasswordModel changePasswordModel) throws Exception {

        User user = userRepository.findByUsername(changePasswordModel.getUsername());
        if (user == null) {
            throw new BusinessException(BusinessExceptionKeyImpl.USER_NOT_FOUND);
        }

        if (!changePasswordModel.getNewPassword().equals(changePasswordModel.getRepeatNewPassword())) {
            throw new BusinessException(BusinessExceptionKeyImpl.PASSWORD_IS_MISMATCH);
        }
        if (changePasswordModel.getNewPassword().length() < 8) {
            throw new BusinessException(BusinessExceptionKeyImpl.PASSWORD_IS_INVALID);
        }
        String newPassword = passwordEncoder.encode(changePasswordModel.getNewPassword());
        user.setPassword(newPassword);
        userRepository.save(user);
        return new PrimitiveResponse(Boolean.TRUE);
    }


    @Override
    public UserOutPutModel checkRole(String nationalCode) throws Exception {
        User user = userRepository.findByNationalCode(nationalCode);
        if (user == null) {
            throw new BusinessException(BusinessExceptionKeyImpl.USER_NOT_FOUND);
        }
        UserOutPutModel userFind = new UserOutPutModel();
        userFind.setRole(user.getRole());
        return userFind;
    }
    @Override
    public Page<UserModel> userListGrid(SearchFilterModel filterModel) throws Exception {
        List<SearchFilterRestrictionModel> restrictionList = new ArrayList<>();

//        restrictionList.add(new SearchFilterRestrictionModel(
//                "phone",
//                SearchFilterOperationEnum.EQUAL,
//                loggedInService.getUsername(),
//                SearchFilterNextConditionOperatorEnum.AND
//        ));

        filterModel.getSearchFilterBoxList().add(new SearchFilterBox(restrictionList, SearchFilterNextConditionOperatorEnum.AND));
        return userRepository.findAll(filterModel.makeSpecification(new UserSpecification()), filterModel.makePageable())
                .map(this::convertEntityToUserModel);
    }


    public UserModel convertEntityToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setNationalCode(user.getNationalCode());
        userModel.setUsername(user.getUsername());
        userModel.setId(user.getId());
        return userModel;
    }

    @Override
    public CustomComboModel genderCustomCombo(CustomComboFilterModel model) throws Exception {
        Long count = 0L;

        List<CustomComboDataRowModel> dataList = new ArrayList<>();
        count = Arrays.stream(GenderEnum.values()).toList().stream().count();
        List<GenderEnum> genderEnums = Arrays.stream(GenderEnum.values()).toList();
        if (!genderEnums.isEmpty()) {

            for (GenderEnum item : genderEnums) {

                CustomComboDataRowModel customComboDataRowModel = new
                        CustomComboDataRowModel(item.getValue());
                dataList.add(customComboDataRowModel);
            }
        }
        return new CustomComboModel(dataList, Math.toIntExact((Long) count), model.getPage(), model.getRows());
    }

    @Override
    public FindNameModel findNameByLogin(String username) throws Exception {
       User user=userRepository.findByUsername(username);
       if(user==null){
           throw  new BusinessException(BusinessExceptionKeyImpl.USER_NOT_FOUND);
       }

       FindNameModel findNameModel=new FindNameModel();
       findNameModel.setFirstName(user.getFirstName());
       findNameModel.setLastName(user.getLastName());
       findNameModel.setUsername(user.getUsername());
       return findNameModel;
    }
}