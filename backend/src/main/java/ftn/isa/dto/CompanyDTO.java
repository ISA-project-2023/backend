package ftn.isa.dto;

import ftn.isa.domain.Company;

public class CompanyDTO {
    private Integer Id;
    private String Name;
    private String Location;
    private Float Grade;

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

    public CompanyDTO(Company company){
        this.Id = company.getId();
        this.Grade = company.getGrade();
        this.Location = company.getLocation();
        this.Name = company.getName();
    }
    public CompanyDTO(Integer id, String name, String location, Float grade) {
        Id = id;
        Name = name;
        Location = location;
        Grade = grade;
    }

}
