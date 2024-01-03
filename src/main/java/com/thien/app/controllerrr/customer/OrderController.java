package com.thien.app.controllerrr.customer;

import com.thien.app.dto.CreateOrderItemRequest;
import com.thien.app.dto.CreateOrderRequest;
import com.thien.app.entity.Order;
import com.thien.app.entity.OrderItem;
import com.thien.app.entity.User;
import com.thien.app.service.OrderItemService;
import com.thien.app.service.OrderService;
import com.thien.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/")
    public String orders(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);
        //get list books
        List<Order> orderList = orderService.getOrderByUserId(user.getId());
        return orderList.toString();
    }
    @PostMapping("/create")
    public RedirectView productDetail(@RequestBody @Valid CreateOrderRequest request, Model model){

//        try {
            // Tạo một đối tượng Order từ request
            Order order = new Order(request.getUserId(), request.getPhone(), request.getAddress(), request.getPrice());;

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

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/customer/orders"); // URL mà bạn muốn chuyển hướng tới
            return redirectView;
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
//        }
    }
}
