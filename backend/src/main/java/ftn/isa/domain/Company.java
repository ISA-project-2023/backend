package ftn.isa.domain;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="\"company\"")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "name", nullable = false)
    private String Name;
    @Column(name = "location", nullable = false)
    private String Location;
    @Column(name = "start_time", nullable = false)
    private LocalTime StartTime;
    @Column(name = "end_time", nullable = false)
    private LocalTime EndTime;
    @Check(constraints = "start_time < end_time")

    @Column(name = "grade", nullable = false)
    private Float Grade;
    @ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.ALL })
    @JoinTable(
            name = "company_equipment",
            joinColumns = { @JoinColumn(name = "company_id") },
            inverseJoinColumns = { @JoinColumn(name = "equipment_id") }
    )
    private Set<Equipment> equipmentInStock = new HashSet<>();
    public Company(){super();}
    public Company(Integer id, String name, String location, Float grade, LocalTime startTime, LocalTime endTime) {
        Id = id;
        Name = name;
        Location = location;
        Grade = grade;
        StartTime = startTime;
        EndTime = endTime;
    }
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getLocation() {
        return Location;
    }
    public void setLocation(String location) {
        Location = location;
    }
    public LocalTime getStartTime() { return StartTime; }
    public void setStartTime(LocalTime startTime) { this.StartTime = startTime; }
    public LocalTime getEndTime() { return EndTime; }
    public void setEndTime(LocalTime endTime) { this.EndTime = endTime; }
    public Float getGrade() {
        return Grade;
    }
    public void setGrade(Float grade) {
        Grade = grade;
    }

    public Set<Equipment> getEquipments() {
        return equipmentInStock;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipmentInStock = equipments;
    }
}
