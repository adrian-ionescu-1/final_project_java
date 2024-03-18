package com.final_project_java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project_java.model.Customer;
import com.final_project_java.model.UserRole;
import com.final_project_java.service.CustomerService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("User created with success.")
    public void createUserTest() throws Exception {

        Customer user = new Customer();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setName("test");
        user.setPassword("1234");
        user.setUserRole(UserRole.ADMIN);

        Mockito.when(customerService.saveCustomer(Mockito.any(Customer.class))).thenReturn(user);

        mockMvc.perform(post("/api/customers/addNewCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("test"))
                .andExpect(jsonPath("$.data.userRole").value("ADMIN"));
    }

    @Test
    @DisplayName("Get users.")
    public void getAllUsersTest() throws Exception {

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

        Mockito.when(customerService.getAllCustomers()).thenReturn(users);

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", IsCollectionWithSize.hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("test1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("test2"));
    }
}
