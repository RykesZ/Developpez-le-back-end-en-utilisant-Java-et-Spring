package com.chatop.portal.controller;

import com.chatop.portal.dto.UserPublic;
import com.chatop.portal.model.LoginParameters;
import net.minidev.json.JSONObject;
import com.chatop.portal.model.User;
import com.chatop.portal.service.JWTService;
import com.chatop.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
      Optional<User> user = userService.getUserById(id);
      if (user.isPresent()) {
        return ResponseEntity.ok(new UserPublic(user.get()));
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilisateur inexistant pour cet id");
      }

    }

    @GetMapping("auth/me")
    public UserPublic getMe() {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return new UserPublic((User)principal);
    }

    @PostMapping("auth/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {

      User createdUser = userService.createUser(user);
      if (createdUser != null) {
          // Génère le JWT
          String token = jwtService.generateToken(user.getEmail());
        // Crée un objet JSON contenant le token
        JSONObject responseJson = new JSONObject();
        responseJson.put("token", token);
        // Renvoie le JWT en réponse
        return ResponseEntity.ok(responseJson.toString());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Informations incorrectes");
      }
    }

    @PostMapping("auth/login")
    public ResponseEntity<Object>  getToken(@RequestBody LoginParameters loginParameters) {
       User authenticatedUser = userService.authenticateUser(loginParameters.getEmail(), loginParameters.getPassword());

        if (authenticatedUser != null) {
            // Génère le JWT
            String token = jwtService.generateToken(authenticatedUser.getEmail());

          // Crée un objet JSON contenant le token
          JSONObject responseJson = new JSONObject();
          responseJson.put("token", token);
            // Renvoie le JWT en réponse
            return ResponseEntity.ok(responseJson.toString());
        } else {
            // Si les identifiants sont incorrects, renvoie une réponse d'erreur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }
    }
}

