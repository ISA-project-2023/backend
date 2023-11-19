package ftn.isa.dto;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;

public class CompanyAdminDTO extends UserDTO {
    private String jobDescription;
    private Company company;

    public String getJobDescription() { return jobDescription; }
    public Company getCompany() { return company; }

    public CompanyAdminDTO() {}
    public CompanyAdminDTO(CompanyAdmin companyAdmin){
        //
        super(companyAdmin.getId(), companyAdmin.getUsername(), companyAdmin.getEmail(), companyAdmin.getPenaltyPoints(), companyAdmin.getRole(),
                companyAdmin.getFirstName(), companyAdmin.getLastName(), companyAdmin.getCategory());
        this.jobDescription = companyAdmin.getJobDescription();
        this.company = companyAdmin.getCompany();
    }
}
