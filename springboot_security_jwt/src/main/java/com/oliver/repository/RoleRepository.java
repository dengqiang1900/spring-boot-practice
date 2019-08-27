package com.oliver.repository;

import com.oliver.domain.Role;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
  Set<Role> findRolesByIdIn(Set<Long> ids);
}
