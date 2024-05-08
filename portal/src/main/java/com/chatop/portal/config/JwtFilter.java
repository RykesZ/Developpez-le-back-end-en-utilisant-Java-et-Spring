package com.chatop.portal.config;

import com.chatop.portal.model.User;
import com.chatop.portal.repository.UserRepository;
import com.chatop.portal.service.JWTService;
import com.chatop.portal.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private UserService userService;

  @Autowired
  private JWTService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String jwt = authorizationHeader.substring(7);
      try {
        // Récupérer les claims permet de vérifier que le token est valide
        Claims claims = jwtService.decodeToken(jwt);
        String email = claims.getSubject();
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> user = userService.getUser(email);
            if (user.isPresent() && validateToken(claims, user.get())) {
              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.get(), null, Collections.emptyList());
              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
      } catch (Exception e) {
        // Si le JWT est expiré, invalide, mal formé, etc.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  private boolean validateToken(Claims claims, User user) {
    String email = claims.getSubject();
    if (email != null) {
      return (email.equals(user.getEmail()) && !isTokenExpired(claims));
    }
    return false;
  }

  private boolean isTokenExpired(Claims claims) {
    final Date expiration = claims.getExpiration();
    return expiration.before(new Date());
  }

}
