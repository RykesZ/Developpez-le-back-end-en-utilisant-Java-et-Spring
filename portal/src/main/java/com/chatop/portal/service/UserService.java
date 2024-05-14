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


}

