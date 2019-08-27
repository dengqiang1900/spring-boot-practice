package com.oliver.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE,generator = "role_ai")
  @TableGenerator(name = "role_ai",//auto_increment
  table = "hibernate_ai",//hibernate生成该表,不使用就所有主键自增长值放一张表
      pkColumnName = "pk_name",//主键字段列名
      valueColumnName = "pk_value",//主键值字段列名
      pkColumnValue = "roleId",//该表主键字段值
      initialValue = 0,//主键值，取出+1做主键，并保存该值
      allocationSize = 0)//（hibernate:hilo生成策略）默认allocationSize50，每次系统重启会将主键生成表里的主键值增加该值？多系统insert数据？
  private Long id;
  private String name;
  @ManyToMany(targetEntity = User.class,mappedBy = "roles")
  private Set<User> users = new HashSet<User>();

  public Role(){}

  public Role(String name) {
    this.name = name;
//    users = new HashSet<User>();
  }

  public Role(String name, Set<User> users) {
    this.name = name;
    this.users = users;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return "Role{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
