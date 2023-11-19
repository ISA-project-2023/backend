package ftn.isa.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "companyAdmin")
public class CompanyAdmin extends User {
    @Column(name = "jobDescription", nullable = false)
    private String jobDescription;
    @ManyToOne
    @JoinColumn(name = "companyId", referencedColumnName = "id")
    private Company company;

    public CompanyAdmin() { super(); }

    public CompanyAdmin(Integer id, String username, String password, String email, Integer penaltyPoints, UserRole role, String firstName, String lastName, String category, Integer id1, String jobDescription, Company company) {
        super(id, username, password, email, penaltyPoints, role, firstName, lastName, category);
        //this.id = id1;
        this.jobDescription = jobDescription;
        this.company = company;
    }
    public CompanyAdmin(User user, Integer id, String jobDescription, Company company){
        super(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPenaltyPoints(), user.getRole(), user.getFirstName(), user.getLastName(), user.getCategory());
        //this.id = id;
        this.jobDescription = jobDescription;
        this.company = company;
    }

    public String getJobDescription() {
        return jobDescription;
    }
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()){
//            return false;
//        }
//        CompanyAdmin a = (CompanyAdmin) o;
//        if (company == null){
//            return false;
//        }
//        return Objects.equals(company, a.company);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company);
    }

    @Override
    public String toString() {
        return "CompanyAdmin [id= "+ getId() +", company= " + company + ", job: " + jobDescription + "]";
    }
}
