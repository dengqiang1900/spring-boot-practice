package com.oliver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
  //测试普通权限
  @PreAuthorize("hasAuthority('visitor')")
  @GetMapping("/visitor")
  public String testVisitor(){
    return "/test/visitor接口调用成功！";
  }

  //测试管理员权限
  @PreAuthorize("hasAuthority('manager')")
  @GetMapping("/manager")
  public String testManager(){
    return "/test/manager接口调用成功！";
  }

  //测试超级管理员权限
  @PreAuthorize("hasAuthority('administrator')")
  @GetMapping("/administrator")
  public String testAdmin(){
    return "/test/administrator接口调用成功！";
  }
}
