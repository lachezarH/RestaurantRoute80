package project.model.entity;

import lombok.Getter;

import javax.persistence.*;


@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

  private String role;


  public RoleEntity() {
  }

  public RoleEntity(String role) {
    this.role = role;
  }

  @Column(name = "role", nullable = false)
  public String getRole() {
    return role;
  }



  public RoleEntity setRole(String role) {
    this.role = role;
    return this;
  }
}
