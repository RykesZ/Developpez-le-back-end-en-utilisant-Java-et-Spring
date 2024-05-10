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
    this.created_at = user.getCreated_at();
    this.updated_at = user.getUpdated_at();

  }

  private Long id;
  private String name;
  private String email;
  private Date created_at;
  private Date updated_at;
}
