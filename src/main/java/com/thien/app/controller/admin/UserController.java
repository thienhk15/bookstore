package com.thien.app.controller.admin;


import com.thien.app.entity.Book;
import com.thien.app.entity.User;
import com.thien.app.repository.UserRepository;
import com.thien.app.service.BookService;
import com.thien.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/accounts")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String users(Model model){
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "admin/table_account";
    }
    @GetMapping("/{userId}")
    public String productDetail(@PathVariable Long userId, Model model){
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "admin/update";
    }
    @DeleteMapping("/delete/{userId}")
    public String deleteBook(@PathVariable Long userId, Model model){
        try{
            User deletedUser = userService.deleteUserById(userId);
            List<User> userList = userService.getAllUsers();
            model.addAttribute("userList", userList);
            return "admin/table_account";
        }catch (Exception e){
            return e.toString();
        }
    }
    @PostMapping("/update")
    public String updateBook(@RequestBody User user, Model model){
        User updateUser;
        try{
            updateUser = userService.updateUser(user);
        }catch (Exception e){
            return e.toString();
        }
        if(updateUser==null){
            model.addAttribute("user", user);
        } else model.addAttribute("book", updateUser);
        return "admin/update";
    }
}
