package ftn.isa.domain;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="penaltyPoints")
    private Integer penaltyPoints;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private UserRole role;
    @Column(name = "category")
    private String category;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;

    public User() {
        super();
    }

    public User(Integer id, String username, String password, String email, Integer penaltyPoints, UserRole role, String firstName, String lastName, String category) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.penaltyPoints = penaltyPoints;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.category = category;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(Integer penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
    }

    public UserRole getRole() {
        return role;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User s = (User) o;
        if (s.username == null || username == null) {
            return false;
        }
        return Objects.equals(username, s.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}
