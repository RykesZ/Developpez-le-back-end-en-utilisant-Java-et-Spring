package com.chatop.portal.service;

import com.chatop.portal.dto.UserPublic;
import com.chatop.portal.model.User;
import com.chatop.portal.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public Optional<User> getUserByEmail(final String email) {
      return userRepository.findUserByEmail(email);
    }

    public Optional<User> getUserById(final Long id) {
      return userRepository.findById(id);
    }

    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        // Recherche de l'utilisateur par email
        User user = userRepository.findUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found with email :" + email));

        // Vérification si l'utilisateur existe et si le mot de passe correspond
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}

