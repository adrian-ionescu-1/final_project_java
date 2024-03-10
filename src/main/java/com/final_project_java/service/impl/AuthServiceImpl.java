package com.final_project_java.service.impl;

import com.final_project_java.dto.LoginDto;
import com.final_project_java.dto.RegisterDto;
import com.final_project_java.exception.BadRequestException;
import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.Customer;
import com.final_project_java.model.UserRole;
import com.final_project_java.repository.CustomerRepository;
import com.final_project_java.service.AuthService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;

    public AuthServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer login(LoginDto loginDto) {
        if (loginDto.getEmail() == null || loginDto.getEmail().isBlank()) {
            throw new BadRequestException("The email is not valid");
        }
        if (loginDto.getPassword() == null || loginDto.getPassword().isBlank()) {
            throw new BadRequestException("The password is not valid");
        }
        Optional<Customer> customerOptional = customerRepository.getCustomerByEmail(loginDto.getEmail());
        customerOptional.orElseThrow(() ->
                new ResourceNotFoundException("The email is not registered"));
        // Verificam daca parolele se potrivesc
        // Metoda checkpw verifica daca parola primita este egala cu parola din baza de date
        boolean isMatch = BCrypt.checkpw(loginDto.getPassword(), customerOptional.get().getPassword());
        if (isMatch == false) {
            throw new BadRequestException("Credential not match");
        }

        return customerOptional.get();
    }

    @Override
    public Customer register(RegisterDto registerDto) {
        // Validare campuri primite in registerDto
        if (registerDto.getUsername() == null || registerDto.getUsername().isBlank()) {
            throw new BadRequestException("The username is invalid");
        }
        if (registerDto.getEmail() == null || registerDto.getEmail().isBlank()) {
            throw new BadRequestException("The email is invalid");
        }
        if (registerDto.getPassword() == null || registerDto.getPassword().isBlank()) {
            throw new BadRequestException("The password is invalid");
        }
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new BadRequestException("Password not match");
        }
        // Verificam sa nu mai existe un cont cu acelasi emial in DB
        Optional<Customer> customerOptional = customerRepository.getCustomerByEmail(registerDto.getEmail());
        if (customerOptional.isPresent()) {
            throw new BadRequestException("Email already exist in DB");
        }
        // Pregatim obiectul de customer cu datele din registerDto pt a fi salvat in DB
        Customer customer = new Customer();
        customer.setName(registerDto.getUsername());
        customer.setEmail(registerDto.getEmail());

        // Metoda hashpw cripteaza parola, pe rpima pozitie avem parola iar pe a doua pozitie avem "cheia de criptare"
        String password = BCrypt.hashpw(registerDto.getPassword(), BCrypt.gensalt());
        customer.setPassword(password);
        customer.setUserRole(UserRole.CUSTOMER);

        return customerRepository.save(customer);
    }
}
