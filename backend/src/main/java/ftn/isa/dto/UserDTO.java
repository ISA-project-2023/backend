package ftn.isa.dto;

import ftn.isa.domain.User;
import ftn.isa.domain.UserRole;

import javax.persistence.Column;

public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private Integer penaltyPoints;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String category;

    public UserDTO(Integer id, String username, String email, Integer penaltyPoints, UserRole role, String firstName, String lastName, String category) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.penaltyPoints = penaltyPoints;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.category = category;
    }

    public UserDTO(){}

    public UserDTO(User user){
        this(user.getId(), user.getUsername(), user.getEmail(), user.getPenaltyPoints(), user.getRole(), user.getFirstName(), user.getLastName(), user.getCategory());
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPenaltyPoints() {
        return penaltyPoints;
    }

    public UserRole getRole() {
        return role;
    }
    public String getCategory(){ return category; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
