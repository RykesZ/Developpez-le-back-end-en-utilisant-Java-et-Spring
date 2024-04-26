package com.chatop.portal.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.chatop.portal.model.User;
import com.chatop.portal.service.JWTService;
import com.chatop.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;


    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?>  getToken(@RequestBody String email, String password) {
        User authenticatedUser = userService.authenticateUser(email, password);

        if (authenticatedUser != null) {
            // Génère le JWT
            String token = jwtService.generateToken(authenticatedUser.getEmail());
            // Renvoie le JWT en réponse
            return ResponseEntity.ok(token);
        } else {
            // Si les identifiants sont incorrects, renvoie une réponse d'erreur
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }
    }
}

