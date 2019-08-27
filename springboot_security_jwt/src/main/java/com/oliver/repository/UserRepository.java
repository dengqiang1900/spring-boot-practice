package com.oliver.repository;


import com.oliver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
  User findUserById(Long id);
  User findUserByUsername(String username);
  User findUserByUsernameAndPassword(String username,String password);
}
