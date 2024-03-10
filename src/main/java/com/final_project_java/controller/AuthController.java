package com.final_project_java.controller;

import com.final_project_java.dto.LoginDto;
import com.final_project_java.dto.RegisterDto;
import com.final_project_java.model.Customer;
import com.final_project_java.service.AuthService;
import com.final_project_java.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //TODO: Implement forgot password

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
        Customer customer = authService.login(loginDto);

        return ResponseEntity.ok(ApiResponse.success("Welcome", customer));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);

        return ResponseEntity.ok(ApiResponse.success("Register with success", null));
    }
}
