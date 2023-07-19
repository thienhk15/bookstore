package com.thien.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Getter
@ToString
@MappedSuperclass
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Date createDate;
    private Date modifiedDate;
}
