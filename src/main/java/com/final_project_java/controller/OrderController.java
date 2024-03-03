package com.final_project_java.controller;


import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.Customer;
import com.final_project_java.model.Order;
import com.final_project_java.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders") // http://localhost:8080/api/orders
public class OrderController {
    private final OrderService orderService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        if (orderList.isEmpty()) {
            throw new ResourceNotFoundException("No orders found in DB");
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/ordersById/{id}")
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable Long id) {
        Optional<Order> orderById = orderService.getOrderById(id);
        orderById.orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " doesn't exist in DB"));
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @GetMapping("/ordersByDate/{date}")
    public ResponseEntity<List<Order>> getAllOrdersByDate(@PathVariable LocalDate date) {
        List<Order> ordersByDateList = orderService.getOrdersByDate(date);
        if (ordersByDateList.isEmpty()) {
            throw new ResourceNotFoundException("There are no orders made in " + date);
        }
        return new ResponseEntity<>(ordersByDateList, HttpStatus.OK);
    }

    @PostMapping("/addNewOrder")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        Order newOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        if (order.getId() == null) {
            throw new ResourceNotFoundException("Order id is not valid");
        }
        Optional<Order> orderOptional = orderService.getOrderById(order.getId());
        orderOptional.orElseThrow(() ->
                new ResourceNotFoundException("Order with id: " + order.getId() + " doesn't exist in DB"));
        return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        orderOptional.orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " doesn't exist in DB"));
        orderService.deleteOrderById(id);
        return new ResponseEntity<>("Order with id: " + id + " delete successfully", HttpStatus.OK);
    }
}
