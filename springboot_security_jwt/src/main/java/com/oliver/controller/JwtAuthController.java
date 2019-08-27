package com.oliver.controller;

import com.oliver.common.Const;
import com.oliver.domain.User;
import com.oliver.service.AuthService;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class JwtAuthController {

  @Autowired
  private AuthService authService;

  //登录
  @PostMapping("/login")
  public String createToken(String username,String password) throws AuthenticationException {
    return authService.login(username,password);//登录成功返回token
  }

  //注册
  @PostMapping("/register")
  public User register(@RequestBody User user) throws AuthenticationException {
    return authService.register(user);
  }

  //登出
  @PostMapping("/logout")
  public String expireToken(HttpServletRequest request) throws AuthenticationException{
    String authHeader = request.getHeader(Const.HEADER_STRING);
    if (authHeader != null && authHeader.startsWith(Const.TOKEN_PREFIX)) {
      final String authToken = authHeader.substring(Const.TOKEN_PREFIX.length());// The part after "Bearer "
      return authService.logout(authToken);
    }
    return null;
  }
}
