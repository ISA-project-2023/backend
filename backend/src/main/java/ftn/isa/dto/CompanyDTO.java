package ftn.isa.dto;

import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class CompanyDTO {
    private Integer id;
    private String name;
    private String location;
    private String startTime;
    private String endTime;
    private Float grade;
    private Set<Equipment> equipmentInStock;
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public Float getGrade() { return grade; }
    public Set<Equipment> getEquipment() { return equipmentInStock; }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipmentInStock = equipment;
    }
    public CompanyDTO() { }

    public CompanyDTO(Company company){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.id = company.getId();
        this.name = company.getName();
        this.location = company.getLocation();
        this.startTime = company.getStartTime().format(formatter);
        this.endTime = company.getEndTime().format(formatter);
        this.grade = company.getGrade();
        this.equipmentInStock = company.getEquipments();
    }
    public CompanyDTO(Integer Id, String Name, String Location, String StartTime, String EndTime, Float Grade) {
        id = Id;
        name = Name;
        location = Location;
        startTime = StartTime;
        endTime = EndTime;
        grade = Grade;
        equipmentInStock = new HashSet<>();
    }
}
