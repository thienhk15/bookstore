package com.thien.app.service;

import com.thien.app.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long userId);
    List<Order> getOrderByUserId(Long id);
    Order createOrder(Order order);
    Order updateOrder(Order order);
    void deleteOrderByOrderId(Long id);
}
