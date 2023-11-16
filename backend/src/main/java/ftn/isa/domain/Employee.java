package ftn.isa.domain;


import ftn.isa.dto.UserDTO;

import javax.persistence.*;

@Entity
@Table(name="employee")
public class Employee extends User {
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "companyInfo")
    private String companyInfo;

    public Employee() {
        super();
    }
    public Employee(Integer id, String username, String password, String email, Integer penaltyPoints, UserRole role, String firstName, String lastName, String category,
                    String city, String country, String phoneNumber, String companyInfo) {
        super(id, username, password, email, penaltyPoints, role, firstName, lastName, category);
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.companyInfo = companyInfo;
    }

    public Employee(UserDTO userDTO, String city, String country, String phoneNumber, String companyInfo) {
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    @Override
    public String toString() {
        return "Employee [id=" + getId() + ", username=" + getUsername() + ", firstName=" + getFirstName() + ", lastName=" + getLastName() +
                ", city=" + city + ", country=" + country + ", phoneNumber=" + phoneNumber + ", companyInfo=" + companyInfo + "]";
    }
}
