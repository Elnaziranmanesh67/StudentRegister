package com.example.studentregister.user.service;


import com.example.studentregister.user.controller.model.UserCreateModel;
import com.example.studentregister.user.controller.model.UserModel;
import com.example.studentregister.user.controller.model.UserOutPutModel;
import com.example.studentregister.user.orm.User;
import com.example.studentregister.user.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Qualifier("UserServiceImpl")
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final LoggedInService loggedInService;
//    private final UserRolesAPIsRepository userRolesAPIsRepository;

    @Override
    public UserCreateModel create(UserCreateModel userModel) throws Exception {

        User user = userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            throw new Exception("USER_REGISTER_BEFORE");
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
        userRepository.save(user);
        return user;
    }

    public UserCreateModel convertEntityToModel(User user) {
        UserCreateModel userCreateModel = new UserCreateModel();
        BeanUtils.copyProperties(user, userCreateModel);
        return userCreateModel;
    }

//    public PrimitiveResponse changePassword(ChangePasswordModel changePasswordModel) throws Exception {
//        User user = userRepository.findByUsername(loggedInService.getUsername());
//        if (user == null) {
//            throw new BusinessException(BusinessExceptionKeyImpl.USER_NOT_FOUND);
//        }
//
//        if (!changePasswordModel.getNewPassword().equals(changePasswordModel.getRepeatNewPassword())) {
//            throw new BusinessException(BusinessExceptionKeyImpl.PASSWORD_IS_MISMATCH);
//        }
//
//        if (changePasswordModel.getNewPassword().length() < 8) {
//            throw new BusinessException(BusinessExceptionKeyImpl.PASSWORD_IS_INVALID);
//        }
//
//        String newPassword = passwordEncoder.encode(changePasswordModel.getNewPassword());
//        user.setPassword(newPassword);
//        userRepository.save(user);
//        return new PrimitiveResponse(Boolean.TRUE);
//    }


    @Override
    public UserOutPutModel checkRole(UserModel userModel) throws Exception {
        User user = userRepository.findByNationalCodeAndUsername(userModel.getNationalCode(), userModel.getUsername());
        if (user == null) {
            throw new Exception("USER_NOT_FOUND");
        }
        UserOutPutModel userFind = new UserOutPutModel();
        userFind.setRole(user.getRole());
        return userFind;
    }

}
