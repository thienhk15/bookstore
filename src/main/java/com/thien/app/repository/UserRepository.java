package com.thien.app.repository;

import com.thien.app.entity.Book;
import com.thien.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);
    Optional<User> findByEmail(String email);
    List<User> findByIdIn(List<Long> ids);
    User save(User user);
    Boolean existsByEmail(String email);

//    User findByUsername(String username);
}
