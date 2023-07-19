package com.thien.app.service.impl;

import com.thien.app.entity.Order;
import com.thien.app.repository.OrderRepository;
import com.thien.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        }
        return null;
    }

    @Override
    public List<Order> getOrderByUserId(Long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList;
    }

    @Override
    public Order createOrder(Order order) {
        Optional<Order> optionalOrder = orderRepository.findById(order.getId());
        if(optionalOrder.isEmpty()){
            return orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("OrderId existed!");
        }
    }

    @Override
    public Order updateOrder(Order order) {
        Optional<Order> optionalOrder = orderRepository.findById(order.getId());
        if(optionalOrder.isEmpty()){
            throw new IllegalArgumentException("Order doesn't exist! orderId: "+ order.getId());
        }
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrderByOrderId(Long id) {
        orderRepository.deleteById(id);
    }


}
