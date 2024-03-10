package com.final_project_java.controller;

import com.final_project_java.exception.ResourceNotFoundException;
import com.final_project_java.model.Order;
import com.final_project_java.service.OrderService;
import com.final_project_java.utils.ApiResponse;
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

    @GetMapping("/getAllOrders")
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        if (orderList.isEmpty()) {
            throw new ResourceNotFoundException("No orders found in DB");
        }
        return ResponseEntity.ok(ApiResponse.success("Order list", orderList));
    }

    @GetMapping("/ordersById/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id) {
        Optional<Order> orderById = orderService.getOrderById(id);
        orderById.orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " doesn't exist in DB"));
        return ResponseEntity.ok(ApiResponse.success("Order by id", orderById.get()));
    }

    @GetMapping("/ordersByDate/{date}")
    public ResponseEntity<ApiResponse> getAllOrdersByDate(@PathVariable LocalDate date) {
        List<Order> ordersByDateList = orderService.getOrdersByDate(date);
        if (ordersByDateList.isEmpty()) {
            throw new ResourceNotFoundException("There are no orders made in " + date);
        }
        return ResponseEntity.ok(ApiResponse.success("Order by data", ordersByDateList));
    }

    @PostMapping("/addNewOrder")
    public ResponseEntity<ApiResponse> saveOrder(@RequestBody Order order) {
        Order newOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(ApiResponse.success("Add new order", orderService.saveOrder(order)));
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<ApiResponse> updateOrder(@RequestBody Order order) {
        if (order.getId() == null) {
            throw new ResourceNotFoundException("Order id is not valid");
        }
        Optional<Order> orderOptional = orderService.getOrderById(order.getId());
        orderOptional.orElseThrow(() ->
                new ResourceNotFoundException("Order with id: " + order.getId() + " doesn't exist in DB"));
        return ResponseEntity.ok(ApiResponse.success("Update order", orderService.updateOrder(order)));
    }

    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        orderOptional.orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " doesn't exist in DB"));
        orderService.deleteOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Order with id: " + id + " doesn't exist in DB", null));
    }
}
