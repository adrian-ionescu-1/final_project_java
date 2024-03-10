package com.final_project_java.repository;

import com.final_project_java.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> getCustomersByName(String name);
    Optional<Customer> getCustomerByEmail(String email);
}
