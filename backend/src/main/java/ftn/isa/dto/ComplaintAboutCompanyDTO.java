package ftn.isa.dto;

import ftn.isa.domain.*;

public class ComplaintAboutCompanyDTO {

    private int id;
    private Company company;
    private String text;
    private Customer customer;
    private boolean isAnswered = false;

    public ComplaintAboutCompanyDTO(ComplaintAboutCompany c){
        this.company = c.getCompany();
        this.text = c.getText();
        this.customer = c.getCustomer();
        this.isAnswered = c.isAnswered();
    }
    public ComplaintAboutCompanyDTO(int id, Company company, String text, Customer customer, boolean isAnswered) {
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
