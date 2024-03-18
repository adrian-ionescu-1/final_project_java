package com.final_project_java.service;

import com.final_project_java.model.Customer;
import com.final_project_java.model.UserRole;
import com.final_project_java.repository.CustomerRepository;
import com.final_project_java.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get users.")
    public void getAllUsers() {

        Customer user1 = new Customer();
        user1.setId(1L);
        user1.setEmail("test1@test.com");
        user1.setName("test1");
        user1.setPassword("1234");
        user1.setUserRole(UserRole.ADMIN);

        Customer user2 = new Customer();
        user2.setId(2L);
        user2.setEmail("test2@test.com");
        user2.setName("test2");
        user2.setPassword("1234");
        user2.setUserRole(UserRole.ADMIN);

        List<Customer> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(customerRepository.findAll()).thenReturn(users);

        List<Customer> resultList = customerService.getAllCustomers();

        assertEquals(2, resultList.size());
        assertEquals("test1", resultList.get(0).getName());
        assertEquals("test2", resultList.get(1).getName());
    }

    @Test
    @DisplayName("User created with success.")
    public void createUserTest() throws Exception {

        Customer user1 = new Customer();
        user1.setEmail("test1@test.com");
        user1.setName("test1");
        user1.setPassword("");
        user1.setUserRole(UserRole.ADMIN);

        customerService.saveCustomer(user1);

        verify(customerRepository, times(1)).save(user1);
    }
}
