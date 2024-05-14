package com.chatop.portal.dto;

import com.chatop.portal.model.Rental;
import lombok.Data;

import java.util.Date;

@Data
public class RentalPublic {

  public RentalPublic(Rental rental) {
    this.id = rental.getId();
    this.name = rental.getName();
    this.surface = rental.getSurface();
    this.price = rental.getPrice();
    this.picture = rental.getPicture();
    this.description = rental.getDescription();
    this.owner_id = rental.getOwner_id();
    this.created_at = rental.getCreated_at();
    this.updated_at = rental.getUpdated_at();
  }

  private Long id;
  private String name;
  private Double surface;
  private Double price;
  private String picture;
  private String description;
  private Long owner_id;
  private Date created_at;
  private Date updated_at;
}
