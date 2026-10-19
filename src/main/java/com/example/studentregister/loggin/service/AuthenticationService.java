package com.example.studentregister.loggin.service;

import com.example.studentregister.loggin.model.AuthenticationRequest;
import com.example.studentregister.loggin.model.AuthenticationResponse;
import com.example.studentregister.loggin.model.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request) throws Exception;
    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;


}
