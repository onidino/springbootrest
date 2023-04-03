package com.in28minutes.springbootrest.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class UserDetails {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String role;

  public UserDetails(String name, String role) {
    super();
    this.name = name;
    this.role = role;
  }

  @Override
  public String toString() {
    return "UserDetails{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", role='" + role + '\'' +
        '}';
  }
}
