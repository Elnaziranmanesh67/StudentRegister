package com.example.studentregister.loggin.service;


import com.example.studentregister.jwtService.JwtService;
import com.example.studentregister.loggin.model.AuthenticationRequest;
import com.example.studentregister.loggin.model.AuthenticationResponse;
import com.example.studentregister.loggin.model.RegisterRequest;
import com.example.studentregister.user.controller.model.UserModel;
import com.example.studentregister.user.orm.User;
import com.example.studentregister.user.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Qualifier("AuthenticationServiceImpl")
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManger;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) throws Exception {

        User user = userRepository.findByNationalCodeAndUsername(request.getNationalCode(), request.getUsername());
        if (user != null) {
            throw new Exception("USER_REGISTRED_BEFORE");
        }

        String password = (request.getPassword() != null) ?
                passwordEncoder.encode(request.getPassword())
                : passwordEncoder.encode("123456789");
        User userNew = new User();
        userNew.setLastName(request.getLastName());
        userNew.setFirstName(request.getFirstName());
        userNew.setRole(request.getRole());
        userNew.setUsername(request.getUsername());
        userNew.setPassword(password);
        userNew.setNationalCode(request.getNationalCode());
        userRepository.save(userNew);

        UserModel userModel = new UserModel();
        userModel.setLastName(user.getLastName());
        userModel.setFirstName(user.getFirstName());
        userModel.setRole(user.getRole());
        userModel.setUsername(user.getUsername());
        userModel.setPassword(password);
        userModel.setNationalCode(user.getNationalCode());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userModel(userModel).build();


    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        try {
            authenticationManger.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
            User user = userRepository.findByUsername(request.getUsername());
            if (user == null) {
                throw new Exception("USER_NOT_FOUND");
            }

            UserModel userModel = new UserModel();
            userModel.setLastName(user.getLastName());
            userModel.setFirstName(user.getFirstName());
            userModel.setRole(user.getRole());
            userModel.setUsername(user.getUsername());
            userModel.setNationalCode(user.getNationalCode());

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .userModel(userModel).build();

        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS");
        }
    }
}
