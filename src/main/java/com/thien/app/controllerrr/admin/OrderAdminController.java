package com.thien.app.controllerrr.admin;

import com.thien.app.entity.Order;
import com.thien.app.entity.OrderItem;
import com.thien.app.service.OrderItemService;
import com.thien.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @GetMapping("")
    public String users(Model model){
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orderList", orderList);
        return "admin/listorders";
    }
    @GetMapping("/{orderId}")
    public String productDetail(@PathVariable Long orderId, Model model){
        try {
            Order order = orderService.getOrderById(orderId);
            List<OrderItem> orderItemList = orderItemService.getOrderItemsByOrderId(orderId);
            model.addAttribute("order", order);
            model.addAttribute("orderItemList", orderItemList);
            return "admin/orderdetails";
        } catch (Exception e){
            return e.toString();
        }
    }
    @DeleteMapping("/delete/{orderId}")
    public String deleteBook(@PathVariable Long orderId, Model model){
        orderService.deleteOrderByOrderId(orderId);
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orderList", orderList);
        return "admin/listorders";
    }
    @PostMapping("/update")
    public String updateBook(@RequestBody Order order, Model model){
        Order updatedOrder;
        try{
            updatedOrder = orderService.updateOrder(order);
        }catch (Exception e){
            return e.toString();
        }
        if(updatedOrder==null){
            model.addAttribute("user", order);
        } else model.addAttribute("book", updatedOrder);
        return "admin/orderdetails";
    }
}
