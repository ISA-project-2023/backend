package ftn.isa.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "systemAdmin")
public class SystemAdmin extends User {
    @Column(name = "isActivated", nullable = false)
    private boolean isActivated;

    public SystemAdmin() { super(); }

    public SystemAdmin(Integer id, String username, String password, String email, Integer penaltyPoints, UserRole role, String firstName,String token, String lastName, String category, Integer id1, boolean isActivated) {
        super(id, username, password, email, penaltyPoints, role, firstName, lastName, category);
        //this.id = id1;
        this.isActivated = isActivated;
    }
    public SystemAdmin(User user, boolean isActivated){
        super(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPenaltyPoints(), user.getRole(), user.getFirstName(), user.getLastName(), user.getCategory());
        //this.id = id;
        this.isActivated = isActivated;
    }

    public boolean getIsActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }
}
