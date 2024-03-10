package com.final_project_java.service;

import com.final_project_java.dto.LoginDto;
import com.final_project_java.dto.RegisterDto;
import com.final_project_java.model.Customer;

public interface AuthService {
    Customer login(LoginDto loginDto);
    Customer register(RegisterDto registerDto);
}
