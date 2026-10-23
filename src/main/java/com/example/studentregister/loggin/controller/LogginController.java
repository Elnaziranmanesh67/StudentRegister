
package com.example.studentregister.loggin.controller;

import com.example.studentregister.loggin.model.AuthenticationRequest;
import com.example.studentregister.loggin.model.AuthenticationResponse;
import com.example.studentregister.loggin.model.RegisterRequest;
import com.example.studentregister.loggin.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogginController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) throws Exception {

        AuthenticationResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) throws Exception {

        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
