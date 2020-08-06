package project.model.service;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserServiceModel extends BaseServiceModel {

    private String username;
    private String password;
    private String email;
    private String role;


    public UserServiceModel() {
    }

    @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters!")
    @NotNull(message = "Incorrect username or password!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 3, max = 20, message = "Password must be between 3 and 20 characters!")

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Email(message = "Enter valid email!")
    @NotNull(message = "Please enter email!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
