package com.chatop.portal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long rental_id;

    @Column(nullable = false, unique = true)
    private Long user_id;

    @Column(nullable = false, unique = true)
    private String message;
}
