package com.thien.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expiredDate;

    public RefreshToken(Long userId, String token, Date expiredDate) {
        this.userId = userId;
        this.token = token;
        this.expiredDate = expiredDate;
    }

    //getters and setters

}