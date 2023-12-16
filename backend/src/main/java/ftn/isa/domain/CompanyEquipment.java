package ftn.isa.domain;

import javax.persistence.*;

@Entity
@Table(name = "\"company_equipment\"")
public class CompanyEquipment {
    @EmbeddedId
    private CompanyEquipmentId id;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToOne
    @MapsId("equipmentId")
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    // Add any additional fields related to this relationship

    public CompanyEquipment() {
        super();
    }

    public CompanyEquipment(Company company, Equipment equipment) {
        this.company = company;
        this.equipment = equipment;
        this.id = new CompanyEquipmentId(company.getId(), equipment.getId());
    }

    public CompanyEquipmentId getId() { return id; }
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
