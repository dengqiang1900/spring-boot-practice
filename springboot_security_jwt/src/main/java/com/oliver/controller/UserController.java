package com.oliver.controller;

import com.oliver.domain.Role;
import com.oliver.domain.User;
import com.oliver.service.RoleService;
import com.oliver.service.UserService;
import java.util.List;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {

  private UserService userService;
  private RoleService roleService;
  @Autowired
  public UserController(UserService userService,RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @PostMapping("/add")
  public String addUser(@RequestBody User user){
    return userService.addUser(user)==1 ? "注册成功！" : "注册失败！";
  }
  @PostMapping("/login")
  public String loginUser(@RequestBody User user, HttpServletResponse response){
    Cookie cookie = new Cookie("loginUser",user.getUsername());
    response.addCookie(cookie);
    return userService.loginUser(user)==1 ? "登录成功！" : "登录失败！";
  }
  @PreAuthorize("hasAuthority('administrator')")
  @PostMapping("/grant/{id}")
  public String grantRoles(@PathVariable("id") Long id,@RequestBody Set<Long> ids){
    Set<Role> roles = roleService.findRolesById(ids);
    if(roles!=null) {
      return userService.grantRoles(id, roles) == true ? "授权成功！" : "授权失败！";
    }else{
      return "授权失败！";
    }
  }

}
