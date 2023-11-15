package ftn.isa.domain;

import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;

import javax.persistence.*;

@Entity
@Table(name = "\"company_equipment\"")
public class CompanyEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    // Add any additional fields related to this relationship

    public CompanyEquipment() {
        super();
    }

    public CompanyEquipment(Company company, Equipment equipment) {
        this.company = company;
        this.equipment = equipment;
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
