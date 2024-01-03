package com.thien.app.controllerrr.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class HomeController {
    @GetMapping("/home")
    public String index(){
        return "index";
    }

}
