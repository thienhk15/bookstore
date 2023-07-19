package com.thien.app.service.impl;

import com.thien.app.entity.OrderItem;
import com.thien.app.repository.OrderItemRepository;
import com.thien.app.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        return orderItemList;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        Optional<OrderItem> optionalOrderItem= orderItemRepository.findByUserIdAndOrderIdAndBookId(orderItem.getUserId(),
                                                                                            orderItem.getOrderId(),
                                                                                            orderItem.getBookId());
        if(optionalOrderItem.isEmpty()){
            return orderItemRepository.save(orderItem);
        }
        else {
            orderItem.setId(optionalOrderItem.get().getOrderId());
            return updateOrderItem(orderItem);
        }
    }

    @Override
    public List<OrderItem> createOrderItems(List<OrderItem> orderItemList) {
        return orderItemRepository.saveAll(orderItemList);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem deleteOrderItem(OrderItem orderItem) {
        Optional<OrderItem> optionalOrderItem= orderItemRepository.findByUserIdAndOrderIdAndBookId(orderItem.getUserId(),
                                                                                                    orderItem.getOrderId(),
                                                                                                    orderItem.getBookId());
        if(optionalOrderItem.isEmpty()){
            throw new IllegalArgumentException("OrderItem not found "+ orderItem.getUserId() + " "+ orderItem.getOrderId()+ " "+ orderItem.getBookId());
        }
        else {
            orderItemRepository.deleteById(orderItem.getId());
            return orderItem;
        }
    }
}
