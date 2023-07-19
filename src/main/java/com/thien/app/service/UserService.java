package com.thien.app.service;

import com.thien.app.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(long id);
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUser(User user);
    User deleteUserById(long id);
}
