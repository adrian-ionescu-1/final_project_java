package com.final_project_java.service.impl;

import com.final_project_java.model.Order;
import com.final_project_java.repository.OrderRepository;
import com.final_project_java.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {

        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    public Order saveOrder(Order order) {

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(Long id) {

        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {

        return orderRepository.getOrdersByDate(date);
    }
}
