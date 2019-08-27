package com.oliver.service;

import com.oliver.domain.User;
import com.oliver.repository.UserRepository;
import com.oliver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserRepository userRepository;

  // 登录
  public String login( String username, String password ) {

    UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken( username, password );

    final Authentication authentication = authenticationManager.authenticate(upToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    final UserDetails userDetails = userDetailsService.loadUserByUsername( username );
    final String token = jwtUtils.generateToken(userDetails);
    return token;
  }

  // 注册
  public User register( User userToAdd ) {

    final String username = userToAdd.getUsername();
    if( userRepository.findUserByUsername(username)!=null ) {
      return null;
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    final String rawPassword = userToAdd.getPassword();
    userToAdd.setPassword( encoder.encode(rawPassword) );
    return userRepository.save(userToAdd);
  }

  //登出,发送过期token？
  public String logout(String token){
    return jwtUtils.generateExpiredToken(token);
  }

  //刷新token
  public String refresh(String oldToken){
    if(jwtUtils.canTokenBeRefreshed(oldToken)){
      return jwtUtils.refreshToken(oldToken);
    }
    return null;
  }
}
