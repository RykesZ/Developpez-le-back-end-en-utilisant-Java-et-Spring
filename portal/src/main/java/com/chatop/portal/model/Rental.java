package com.chatop.portal.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rentals")
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Double surface;

    @Column
    private double price;

    @Column
    private String picture;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date created_at;

    @Column(nullable = false)
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}
