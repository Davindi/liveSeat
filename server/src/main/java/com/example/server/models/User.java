package com.example.server.models;


import com.example.server.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private String firstname;

    public User() {}

    public User(String username, String password, String email, UserRole role , String firstname ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.firstname = firstname;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public UserRole getRole() {return role;}
    public void setRole(UserRole role) {this.role = role;}

    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}
}
