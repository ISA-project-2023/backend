package ftn.isa.domain;

import javax.persistence.*;
@Entity
@Table(name="\"complaintAboutAdmin\"")
public class ComplaintAboutAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "id")
    private CompanyAdmin admin;

    @Column(name="text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Customer customer;

    @Column(name="isAnswered", nullable = false)
    private boolean isAnswered = false;

    public ComplaintAboutAdmin(){super();}
    public ComplaintAboutAdmin(int id, CompanyAdmin admin, String text, Customer customer, boolean isAnswered) {
        this.id = id;
        this.admin = admin;
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

    public CompanyAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(CompanyAdmin admin) {
        this.admin = admin;
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
