package project.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private List<RoleEntity> roles;

    public User(String username, String password, String email, List<RoleEntity> roles){
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }


    public User() {
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
