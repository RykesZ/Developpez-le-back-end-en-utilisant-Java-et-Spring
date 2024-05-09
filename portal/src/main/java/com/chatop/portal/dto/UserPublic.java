package com.chatop.portal.dto;

import com.chatop.portal.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserPublic {

  public UserPublic(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.createdAt = user.getCreated_at();
    this.updatedAt = user.getUpdated_at();

  }

  private Long id;
  private String name;
  private String email;
  private Date createdAt;
  private Date updatedAt;
}
