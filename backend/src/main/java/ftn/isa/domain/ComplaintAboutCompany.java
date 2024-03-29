package ftn.isa.domain;

import javax.persistence.*;

@Entity
@Table(name="\"complaintAboutCompany\"")
public class ComplaintAboutCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "companyId", referencedColumnName = "id")
    private Company company;

    @Column(name="text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Customer customer;

    @Column(name="isAnswered", nullable = false)
    private boolean isAnswered = false;
    public ComplaintAboutCompany(){super();}

    public ComplaintAboutCompany(int id, Company company, String text, Customer customer, boolean isAnswered) {
        this.id = id;
        this.company = company;
        this.text = text;
        this.customer = customer;
        this.isAnswered = isAnswered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
