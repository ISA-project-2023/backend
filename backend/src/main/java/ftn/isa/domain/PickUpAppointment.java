package ftn.isa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="\"pickUpAppointment\"")
public class PickUpAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="date", nullable=false)
    private LocalDateTime date;
    @Column(name="duration", nullable = false)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "companyAdminId", referencedColumnName = "id")
    private User companyAdmin;
    public PickUpAppointment(Integer id, LocalDateTime date, Integer duration, User companyAdmin) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.companyAdmin = companyAdmin;
    }
    public PickUpAppointment(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public User getCompanyAdmin() {
        return companyAdmin;
    }

    public void setCompanyAdmin(User companyAdmin) {
        this.companyAdmin = companyAdmin;
    }
}
