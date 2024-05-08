package com.chatop.portal.model;

import lombok.Data;

import jakarta.persistence.*;

import java.io.File;
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
    private Double price;

    @Column
    private String picture;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date created_at;

    @Column(nullable = false)
    private Date updated_at;

    @Column
    private Long ownerId;
}
