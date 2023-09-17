package com.example.preventduplicatelogin.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Column(name = "id")
    private String userId;
    private String password;
    private LocalDateTime regDate;
}
