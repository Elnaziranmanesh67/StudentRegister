
package com.example.studentregister.loggin.service;

import com.example.studentregister.jwtService.JwtService;
import com.example.studentregister.loggin.model.AuthenticationRequest;
import com.example.studentregister.loggin.model.AuthenticationResponse;
import com.example.studentregister.loggin.model.RegisterRequest;
import com.example.studentregister.user.controller.model.UserModel;
import com.example.studentregister.user.orm.RoleEnum;
import com.example.studentregister.user.orm.User;
import com.example.studentregister.user.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request)
            throws Exception {

        if (request == null
                || request.getUsername() == null
                || request.getUsername().isBlank()
                || request.getNationalCode() == null
                || request.getNationalCode().isBlank()
                || request.getPassword() == null
                || request.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "نام کاربری، کد ملی و رمز عبور الزامی هستند"
            );
        }

        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new IllegalArgumentException(
                    "این نام کاربری قبلاً ثبت شده است"
            );
        }

        if (userRepository.findByNationalCode(request.getNationalCode()) != null) {
            throw new IllegalArgumentException(
                    "این کد ملی قبلاً ثبت شده است"
            );
        }

        if (request.getRole() == null) {
            throw new IllegalArgumentException("نقش کاربر الزامی است");
        }

        if (request.getRole() == null
                || (request.getRole() != RoleEnum.STUDENT
                && request.getRole() != RoleEnum.TEACHER)) {

            throw new IllegalArgumentException(
                    "نقش انتخاب‌شده معتبر نیست"
            );
        }
        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setNationalCode(request.getNationalCode().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        UserModel userModel = toUserModel(savedUser);
        String jwtToken = jwtService.generateToken(savedUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userModel(userModel)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request)
            throws Exception {

        if (request == null
                || request.getUsername() == null
                || request.getUsername().isBlank()
                || request.getPassword() == null
                || request.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "نام کاربری و رمز عبور الزامی هستند"
            );
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(
                    "نام کاربری یا رمز عبور اشتباه است"
            );
        }

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new BadCredentialsException(
                    "نام کاربری یا رمز عبور اشتباه است"
            );
        }

        UserModel userModel = toUserModel(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userModel(userModel)
                .build();
    }

    private UserModel toUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setUsername(user.getUsername());
        userModel.setNationalCode(user.getNationalCode());
        userModel.setRole(user.getRole());
        return userModel;
    }
}
