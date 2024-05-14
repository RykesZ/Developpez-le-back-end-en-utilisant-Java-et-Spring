package com.chatop.portal.service;

import com.chatop.portal.model.User;
import com.chatop.portal.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public User authenticateUser(String email, String password) {
    User user = userRepository.findUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found with email :" + email));

    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
      return user;
    } else {
      return null;
    }
  }

}
