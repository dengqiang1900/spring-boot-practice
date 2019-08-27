package com.oliver.service;

import com.oliver.domain.Role;
import com.oliver.domain.User;
import com.oliver.repository.UserRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public int addUser(User user){
    return userRepository.save(user)!=null?1:0;
  }

  public int loginUser(User user){
    return userRepository.findUserByUsernameAndPassword(user.getUsername(),user.getPassword())!=null?1:0;
  }

  public User findUserById(Long id){
    return userRepository.findUserById(id);
  }

  public boolean grantRoles(Long id,Set<Role> roles){
    User user = findUserById(id);
    if(user!=null){
      if(user.getRoles().addAll(roles)){
        userRepository.save(user);//将roles写入中间表user_role
       return true;
      }
    }
    return false;
  }

  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userRepository.findUserByUsername(s);
    if(user==null){
      throw new UsernameNotFoundException("用户不存在！");
    }
    return user;
  }
}
