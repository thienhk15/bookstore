package com.thien.app.controllerrr;

import com.thien.app.dto.Test;
import com.thien.app.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("")
public class MainController {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @GetMapping
    public RedirectView home() {
        return new RedirectView("/customer/home");
    }
    @GetMapping("/401")
    public ResponseEntity<String> thien(){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This page requires authentication. Please login or refresh token to access.");
    }
    @GetMapping("/test/{userId}")
    @ResponseBody
    public ResponseEntity<?> test(@PathVariable Long userId) {
        List<Test> testList = orderItemRepository.findOrderItemsByUserId(userId);
        return ResponseEntity.ok(testList);
    }
}
