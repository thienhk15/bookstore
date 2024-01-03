package com.thien.app.controller;

import com.thien.app.dto.UserDto;
import com.thien.app.entity.ErrorResponse;
import com.thien.app.entity.User;
import com.thien.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "userId") Long id ){
        User user = userService.getUserById(id);
        if(user!=null){
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : errors) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            ErrorResponse errorResponse = new ErrorResponse(400, "Validation error", errorMap);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        if(userService.getUserByEmail(userDto.getEmail())!=null){
            ResponseEntity.notFound().build();
        }

        User user = new User(userDto.getEmail(),
                userDto.getPassword(),
                userDto.getImage(),
                userDto.getName(),
                userDto.getBirthday(),
                userDto.getPhone());
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<User> deleteBookById(@PathVariable Long userId) {
        User deletedUser = userService.deleteUserById(userId);
        if (deletedUser != null) {
            return ResponseEntity.ok(deletedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/update")
    public ResponseEntity<User> updateBook(@RequestParam Long id,
                                           @RequestParam(required = false) String password,
                                           @RequestParam(required = false) String image,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) Date birthday,
                                           @RequestParam(required = false) String phone
    ){

        User user = userService.getUserById(id);
        if(user==null){
            return ResponseEntity.notFound().build();
        }

        if (password != null) {
            user.setPassword(password);
        }
        if (image != null) {
            user.setImage(image);
        }
        if (name != null) {
            user.setName(name);
        }
        if (birthday != null) {
            user.setBirthday(birthday);
        }
        if (phone != null) {
            user.setPhone(phone);
        }

        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

}
