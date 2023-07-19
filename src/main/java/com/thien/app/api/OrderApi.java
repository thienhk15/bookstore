package com.thien.app.api;

import com.thien.app.dto.CreateOrderItemRequest;
import com.thien.app.dto.CreateOrderRequest;
import com.thien.app.entity.Order;
import com.thien.app.entity.OrderItem;
import com.thien.app.repository.OrderItemRepository;
import com.thien.app.repository.OrderRepository;
import com.thien.app.service.OrderItemService;
import com.thien.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderApi {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @GetMapping("")
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            // Tạo một đối tượng Order từ request
            Order order = new Order();
            order.setUserId(request.getUserId());

            // Lưu đơn hàng vào cơ sở dữ liệu
            orderService.createOrder(order);

            // Tạo các đối tượng OrderItem từ request
            List<OrderItem> orderItems = new ArrayList<>();
            for (CreateOrderItemRequest itemRequest : request.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(order.getId());
                orderItem.setBookId(itemRequest.getBookId());
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItems.add(orderItem);
            }

            // Lưu các mục đơn hàng vào cơ sở dữ liệu
            orderItemService.createOrderItems(orderItems);

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }
}
