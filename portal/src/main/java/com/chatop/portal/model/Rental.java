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

  @Column(name = "created_at", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date created_at;

  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updated_at;

  @PrePersist
  protected void onCreate() {
    this.created_at = new Date();
    this.updated_at = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updated_at = new Date();
  }

    @Column
    private Long owner_id;
}
