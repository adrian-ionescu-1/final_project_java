package com.final_project_java.controller;


import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.Customer;
import com.final_project_java.model.Item;
import com.final_project_java.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/customers") // http://localhost:8080/api/customers
public class CustomerController {
    private final CustomerService customerService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/getAllCustomers") // http://localhost:8080/api/customers/getAllCustomers
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerList = customerService.getAllCustomers();
        if (customerList.isEmpty()) {
            throw new ResourceNotFoundException("No customers found in DB");
        }
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/customersById/{id}") // http://localhost:8080/api/customers/customersById/id
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerById = customerService.getCustomerById(id);
        customerById.orElseThrow(()-> new ResourceNotFoundException("Customer with id: " + id + " doesn't exist in DB"));
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/customersByName/{name}") //http://localhost:8080/api/customers/customersByName/{name}
    public ResponseEntity<List<Customer>> getAllCustomersByName(@PathVariable String name) {
        List<Customer> customersByName = customerService.getCustomersByName(name);
        if (customersByName.isEmpty()) {
            throw new ResourceNotFoundException("The client with name : " + name + " doesn't exist in DB");
        }
        return new ResponseEntity<>(customersByName, HttpStatus.OK);
    }

    @PostMapping("/addNewCustomer") // http://localhost:8080/api/customers/addNewCustomer
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    @PutMapping("/updateCustomer") // http://localhost:8080/api/customers/updateCustomer
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        if (customer.getId() == null) {
            throw new ResourceNotFoundException("Customer id is not valid");
        }
        Optional<Customer> customerOptional = customerService.getCustomerById(customer.getId());
        customerOptional.orElseThrow(() ->
                new ResourceNotFoundException("Customer with id: " + customer.getId() + " doesn't exist in DB"));
        return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer/{id}") // http://localhost:8080/api/customers/deleteCustomer
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.getCustomerById(id);
        customerOptional.orElseThrow(()-> new ResourceNotFoundException("Customer with id: " + id + " doesn't exist in DB"));
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>("Customer with id: " + id + " delete successfully", HttpStatus.OK);
    }
}
