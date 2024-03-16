package com.final_project_java.controller;

import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.Customer;
import com.final_project_java.service.CustomerService;
import com.final_project_java.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/customers") // http://localhost:8080/api/customers
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCustomers() {
        List<Customer> customerList = customerService.getAllCustomers();
        if (customerList.isEmpty()) {
            throw new ResourceNotFoundException("No customers found in DB");
        }
//        ApiResponse response = new ApiResponse.Builder()
//                .status(HttpStatus.OK.value())
//                .message("Customer list")
//                .data(customerList)
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.OK);

        return ResponseEntity.ok(ApiResponse.success("Customer list", customerList));
    }

    @GetMapping("/customersById/{id}")
    public ResponseEntity<ApiResponse> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerById = customerService.getCustomerById(id);
        customerById.orElseThrow(() -> new ResourceNotFoundException("Customer with id: " + id + " doesn't exist in DB"));

        return ResponseEntity.ok(ApiResponse.success("Customer by id", customerById.get()));
    }

    @GetMapping("/customersByName/{name}")
    public ResponseEntity<ApiResponse> getAllCustomersByName(@PathVariable String name) {
        List<Customer> customersByName = customerService.getCustomersByName(name);
        if (customersByName.isEmpty()) {
            throw new ResourceNotFoundException("The client with name : " + name + " doesn't exist in DB");
        }

        return ResponseEntity.ok(ApiResponse.success("Customer by name", customersByName));
    }

    @PostMapping("/addNewCustomer")
    public ResponseEntity<ApiResponse> saveCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.saveCustomer(customer);

        return ResponseEntity.ok(ApiResponse.success("Add customer", newCustomer));
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<ApiResponse> updateCustomer(@RequestBody Customer customer) {
        if (customer.getId() == null) {
            throw new ResourceNotFoundException("Customer id is not valid");
        }
        Optional<Customer> customerOptional = customerService.getCustomerById(customer.getId());
        customerOptional.orElseThrow(() ->
                new ResourceNotFoundException("Customer with id: " + customer.getId() + " doesn't exist in DB"));

        return ResponseEntity.ok(ApiResponse.success("Update customer", customerService.updateCustomer(customer)));
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.getCustomerById(id);
        customerOptional.orElseThrow(() -> new ResourceNotFoundException("Customer with id: " + id + " doesn't exist in DB"));
        customerService.deleteCustomerById(id);

        return ResponseEntity.ok(ApiResponse.success("Customer with id: " + id + " delete successfully", null));
    }
}
