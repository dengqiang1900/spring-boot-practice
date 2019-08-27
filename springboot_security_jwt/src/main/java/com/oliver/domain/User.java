package com.oliver.domain;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE,generator = "user_ai")
  @TableGenerator(name = "user_ai",//auto_increment
      table = "hibernate_ai",
      pkColumnName = "pk_name",
      valueColumnName = "pk_value",
      pkColumnValue = "userId",
      initialValue = 0,
      allocationSize = 0)
  private Long id;
  private String username;
  private String password;
  private Date lastPasswordResetDate;

  @ManyToMany(targetEntity = Role.class,cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
  //mappedBy 属性定义了此类为双向关系的维护端，注意：mappedBy 属性的值为此关系的另一端的属性名。与下面外键定义冲突
  @JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
  // 使用JoinTable来描述中间表，并描述中间表中外键与User,Role的映射关系
  // joinColumns它是用来描述User与中间表中的映射关系
  // inverseJoinColums它是用来描述Role与中间表中的映射关系
  private Set<Role> roles = new HashSet<Role>();

  public User(){
  }

//  public User(String username, String password) {
//    this.username = username;
//    this.password = password;
////    this.roles = new HashSet<Role>();
//  }
//
//  public User(String username, String password, Set<Role> roles) {
//    this.username = username;
//    this.password = password;
//    this.roles = roles;
//  }

  public String getUsername() {
    return username;
  }
  //账户是否未过期,过期无法验证
  public boolean isAccountNonExpired() {
    return true;
  }
  //指定用户是否解锁,锁定的用户无法进行身份验证
  public boolean isAccountNonLocked() {
    return true;
  }
  //指示是否已过期的用户的凭据(密码),过期的凭据防止认证
  public boolean isCredentialsNonExpired() {
    return true;
  }
  //是否可用 ,禁用的用户不能身份验证
  public boolean isEnabled() {
    return true;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (Role role : roles) {
      authorities.add( new SimpleGrantedAuthority( role.getName() ) );
    }
    return authorities;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Date getLastPasswordResetDate() {
    return lastPasswordResetDate;
  }

  public void setLastPasswordResetDate(Date lastPasswordResetDate) {
    this.lastPasswordResetDate = lastPasswordResetDate;
  }

  @Override
  public String toString() {
    return "User{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
