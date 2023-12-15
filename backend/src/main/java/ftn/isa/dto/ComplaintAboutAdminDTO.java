package ftn.isa.dto;

import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.ComplaintAboutAdmin;
import ftn.isa.domain.Customer;

public class ComplaintAboutAdminDTO {

    private int id;
    private CompanyAdmin admin;
    private String text;
    private Customer customer;
    private boolean isAnswered = false;

    public ComplaintAboutAdminDTO(ComplaintAboutAdmin c){
        this.admin = c.getAdmin();
        this.text = c.getText();
        this.customer = c.getCustomer();
        this.isAnswered = c.isAnswered();
    }
    public ComplaintAboutAdminDTO(int id, CompanyAdmin admin, String text, Customer customer, boolean isAnswered) {
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
