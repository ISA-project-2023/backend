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

    @Column(name = "isVerified", nullable = false)
    private boolean isVerified;

    public CompanyAdmin() { super(); }

    public CompanyAdmin(Integer id, String username, String password, String email, Integer penaltyPoints, UserRole role, String firstName,String token, String lastName, String category, Integer id1, String jobDescription, Company company, boolean verified, double penaltyMonth) {
        super(id, username, password, email, penaltyPoints, role, firstName, lastName, category, penaltyMonth);
        this.jobDescription = jobDescription;
        this.company = company;
        this.isVerified = verified;
    }
    public CompanyAdmin(User user, Integer id, String jobDescription, Company company, boolean verified){
        super(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPenaltyPoints(), user.getRole(), user.getFirstName(), user.getLastName(), user.getCategory(), user.getPenaltyMonth());
        this.jobDescription = jobDescription;
        this.company = company;
        this.isVerified = verified;
    }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }

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
        return "CompanyAdmin [id= "+ getId() +", company= " + company + ", job: " + jobDescription + ", is verified: " + isVerified + "]";
    }
}
