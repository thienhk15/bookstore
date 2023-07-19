package com.thien.app.entity;

import com.thien.app.entity.key.OrderKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "orders")
@NoArgsConstructor
//@IdClass(OrderKey.class)
public class Order {
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
//    @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", initialValue = 0, allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String phone;
    private String address;
    private String price;
    @Column(name = "create_at")
    private Date createAt;
    private String status;

    public Order(Long userId, String phone, String address, String price) {
        this.userId = userId;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.createAt = new Date();
        this.status = "ok";
    }
}
