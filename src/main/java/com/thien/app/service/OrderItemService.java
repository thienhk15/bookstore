package com.thien.app.service;

import com.thien.app.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    OrderItem createOrderItem(OrderItem orderItem);

    List<OrderItem> createOrderItems(List<OrderItem> orderItemList);
    OrderItem updateOrderItem(OrderItem orderItem);
    OrderItem deleteOrderItem(OrderItem orderItem);
}
