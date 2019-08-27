package com.oliver.service;

import com.oliver.domain.Role;
import com.oliver.repository.RoleRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private RoleRepository roleRepository;

  @Autowired
  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role addRole(Role role){
    return roleRepository.save(role);
  }

  public Set<Role> findRolesById(Set<Long> ids){
    return roleRepository.findRolesByIdIn(ids);
  }
}
