package com.thien.app.service.impl;

import com.thien.app.entity.User;
import com.thien.app.repository.UserRepository;
import com.thien.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public User getUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            System.out.println("User not found with emailll: " + email);
            //throw new IllegalArgumentException("User not found with email: " + email);
            return null;
        }
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if(optionalUser.isPresent()){
            return userRepository.save(user);
        }
        else {
            throw new IllegalArgumentException("User not found with id: "+ user.getId());
        }
    }

    @Override
    public User deleteUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User deletedUser = optionalUser.get();
            userRepository.deleteById(id);
            return deletedUser;
        }
        else {
            throw new IllegalArgumentException("User not found with id: "+ id);
        }
    }
}
