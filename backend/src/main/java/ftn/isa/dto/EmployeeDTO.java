package ftn.isa.dto;

import ftn.isa.domain.Employee;
import ftn.isa.domain.UserRole;

public class EmployeeDTO extends UserDTO {
    private String city;
    private String country;
    private String phoneNumber;
    private String companyInfo;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee employee) {
        super(employee.getId(), employee.getUsername(), employee.getEmail(), employee.getPenaltyPoints(), employee.getRole(),
                employee.getFirstName(), employee.getLastName(), employee.getCategory());
        this.city = employee.getCity();
        this.country = employee.getCountry();
        this.phoneNumber = employee.getPhoneNumber();
        this.companyInfo = employee.getCompanyInfo();
    }
    public String getCity() {
        return city;
    }
    public String getCountry() {
        return country;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getCompanyInfo() {
        return companyInfo;
    }
}
