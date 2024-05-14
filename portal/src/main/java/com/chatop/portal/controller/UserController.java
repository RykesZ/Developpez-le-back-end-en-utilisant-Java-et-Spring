package com.chatop.portal.controller;

import com.chatop.portal.dto.UserPublic;
import com.chatop.portal.model.User;
import com.chatop.portal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  private UserService userService;

  @Operation(summary = "Get user infos", description = "Allow the current authenticated user to get another user's infos")
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUser(@PathVariable Long id) {
    Optional<User> user = userService.getUserById(id);
    if (user.isPresent()) {
      return ResponseEntity.ok(new UserPublic(user.get()));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilisateur inexistant pour cet id");
    }
  }

}
