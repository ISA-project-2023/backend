package ftn.isa.domain;

import javax.persistence.*;
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
    @Column(name = "grade", nullable = false)
    private Float Grade;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "company_equipment",
            joinColumns = { @JoinColumn(name = "company_id") },
            inverseJoinColumns = { @JoinColumn(name = "equipment_id") }
    )
    private Set<Equipment> equipment = new HashSet<>();
    public Company(){super();}
    public Company(Integer id, String name, String location, Float grade) {
        Id = id;
        Name = name;
        Location = location;
        Grade = grade;
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

    public Float getGrade() {
        return Grade;
    }

    public void setGrade(Float grade) {
        Grade = grade;
    }

    public Set<Equipment> getEquipments() {
        return equipment;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipment = equipments;
    }
}
