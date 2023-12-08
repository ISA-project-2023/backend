package ftn.isa.dto;

import ftn.isa.domain.Customer;

public class CustomerDTO extends UserDTO {
    private String city;
    private String country;
    private String phoneNumber;
    private String companyInfo;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        super(customer.getId(), customer.getUsername(), customer.getEmail(), customer.getPenaltyPoints(), customer.getRole(),
                customer.getFirstName(), customer.getLastName(), customer.getCategory());
        this.city = customer.getCity();
        this.country = customer.getCountry();
        this.phoneNumber = customer.getPhoneNumber();
        this.companyInfo = customer.getCompanyInfo();
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
