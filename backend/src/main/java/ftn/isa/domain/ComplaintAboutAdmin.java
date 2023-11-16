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
    private Employee admin;

    @Column(name="text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;

    public ComplaintAboutAdmin(){super();}
    public ComplaintAboutAdmin(int id, Employee admin, String text, Employee employee) {
        this.id = id;
        this.admin = admin;
        this.text = text;
        this.employee = employee;
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

    public void setAdmin(Employee admin) {
        this.admin = admin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
