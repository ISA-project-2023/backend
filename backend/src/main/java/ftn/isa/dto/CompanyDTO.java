package ftn.isa.dto;

import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class CompanyDTO {
    private Integer Id;
    private String Name;
    private String Location;
    private LocalTime StartTime;
    private LocalTime EndTime;
    private Float Grade;
    private Set<Equipment> EquipmentInStock;
    public Integer getId() {
        return Id;
    }
    public String getName() {
        return Name;
    }
    public String getLocation() {
        return Location;
    }
    public LocalTime getStartTime() { return StartTime; }
    public LocalTime getEndTime() { return EndTime; }
    public Float getGrade() { return Grade; }
    public Set<Equipment> getEquipment() { return EquipmentInStock; }

    public CompanyDTO(Company company){
        this.Id = company.getId();
        this.Name = company.getName();
        this.Location = company.getLocation();
        this.StartTime = company.getStartTime();
        this.EndTime = company.getEndTime();
        this.Grade = company.getGrade();
        this.EquipmentInStock = company.getEquipments();
    }
    public CompanyDTO(Integer id, String name, String location, LocalTime startTime, LocalTime endTime, Float grade) {
        Id = id;
        Name = name;
        Location = location;
        StartTime = startTime;
        EndTime = endTime;
        Grade = grade;
        EquipmentInStock = new HashSet<>();
    }
}
