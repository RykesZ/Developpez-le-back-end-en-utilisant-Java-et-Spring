package com.chatop.portal.service;

import com.chatop.portal.model.User;
import com.chatop.portal.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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


    public Optional<User> getUser(final String email) {

      return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        // Recherche de l'utilisateur par email
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found with email :" + email));

        // Vérification si l'utilisateur existe et si le mot de passe correspond
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}

