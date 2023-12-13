package ftn.isa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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
    @Column(name="isFree", nullable = false)
    private boolean isFree = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyAdminId", referencedColumnName = "id")
    private CompanyAdmin companyAdmin;
    public PickUpAppointment(Integer id, LocalDateTime date, Integer duration, boolean isFree, CompanyAdmin companyAdmin) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.isFree = isFree;
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

    public boolean isFree() { return isFree; }
    public void setFree(boolean free) { isFree = free; }

    public CompanyAdmin getCompanyAdmin() {
        return companyAdmin;
    }
    public void setCompanyAdmin(CompanyAdmin companyAdmin) {
        this.companyAdmin = companyAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PickUpAppointment e = (PickUpAppointment) o;
        if (    e.date == null || date == null ||
                e.duration == null || duration == null ||
                e.companyAdmin == null || companyAdmin == null) {
            return false;
        }
        return     isFree == e.isFree
                && Objects.equals(id, e.id)
                && Objects.equals(date, e.date)
                && Objects.equals(duration, e.duration)
                && Objects.equals(companyAdmin, e.companyAdmin);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, date, duration, isFree, companyAdmin);
    }
    @Override
    public String toString() {
        return "PickUpAppointment{" +
                "id=" + id +
                ", date=" + date +
                ", duration=" + duration +
                ", isFree=" + isFree +
                ", companyAdmin=" + companyAdmin +
                '}';
    }
}
