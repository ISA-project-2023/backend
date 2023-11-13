package ftn.isa.dto;

import ftn.isa.domain.User;

import javax.persistence.Column;

public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private Integer penaltyPoints;
    private String category;
    private String firstName;
    private String lastName;

    public UserDTO(Integer id, String username, String email, Integer penaltyPoints, String category, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.penaltyPoints = penaltyPoints;
        this.category = category;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(User user){
        this(user.getId(), user.getUsername(), user.getEmail(), user.getPenaltyPoints(), user.getCategory(), user.getFirstName(), user.getLastName());
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

    public String getCategory() {
        return category;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
