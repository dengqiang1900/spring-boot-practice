package com.oliver.controller;

import com.oliver.domain.Role;
import com.oliver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
  private RoleService roleService;
  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PreAuthorize("hasAuthority('administrator')")
  @PostMapping("/add")
  public String addRole(@RequestBody Role role){
    return roleService.addRole(role)!=null?"角色添加成功！":"角色添加失败！";
  }
}
