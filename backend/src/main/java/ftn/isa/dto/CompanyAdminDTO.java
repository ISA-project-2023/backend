package ftn.isa.dto;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;

public class CompanyAdminDTO {
    private Integer id;
    private String jobDescription;
    private Company company;

    public Integer getId() { return id; }
    public String getJobDescription() { return jobDescription; }
    public Company getCompany() { return company; }

    public CompanyAdminDTO() {}
    public CompanyAdminDTO(Integer id, String jobDescription, Company company) {
        //
        this.id = id;
        this.jobDescription = jobDescription;
        this.company = company;
    }
    public CompanyAdminDTO(CompanyAdmin companyAdmin){
        //
        this.id = companyAdmin.getId();
        this.jobDescription = companyAdmin.getJobDescription();
        this.company = companyAdmin.getCompany();
    }
}
