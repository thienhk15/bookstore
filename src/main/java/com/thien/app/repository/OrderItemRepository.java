package com.thien.app.repository;

import com.thien.app.dto.Test;
import com.thien.app.entity.Book;
import com.thien.app.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    List<OrderItem> findByOrderId(Long orderId);
    Optional<OrderItem> findByUserIdAndOrderIdAndBookId(Long userId, Long orderId, Long bookId);
    @Query("SELECT new com.thien.app.dto.Test(o.userId, o.id, oi.bookId, oi.quantity) FROM Order o JOIN OrderItem oi ON o.id = oi.orderId WHERE o.userId = :userId")
    List<Test> findOrderItemsByUserId(@Param("userId") Long userId);
}
