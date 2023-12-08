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
    private Customer admin;

    @Column(name="text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Customer customer;

    public ComplaintAboutAdmin(){super();}
    public ComplaintAboutAdmin(int id, Customer admin, String text, Customer customer) {
        this.id = id;
        this.admin = admin;
        this.text = text;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(Customer admin) {
        this.admin = admin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getEmployee() {
        return customer;
    }

    public void setEmployee(Customer customer) {
        this.customer = customer;
    }
}
