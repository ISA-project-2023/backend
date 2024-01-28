package ftn.isa.dto;

import ftn.isa.domain.CompanyEquipment;
public class CompanyEquipmentDTO {
    private Integer id;
    private CompanyDTO company;
    private EquipmentDTO equipment;
    private Integer quantity;

    public Integer getId() { return id; }
    public CompanyDTO getCompany() { return company; }
    public EquipmentDTO getEquipment() { return equipment; }
    public Integer getQuantity() { return quantity; }

    public CompanyEquipmentDTO(CompanyEquipment companyEquipment) {
        //this.id = companyEquipment.getId();
        this.company = new CompanyDTO(companyEquipment.getCompany());
        this.equipment = new EquipmentDTO(companyEquipment.getEquipment());
        this.quantity = companyEquipment.getQuantity();
    }

    public CompanyEquipmentDTO(Integer id, CompanyDTO company, EquipmentDTO equipment, Integer quantity) {
        this.id = id;
        this.company = company;
        this.equipment = equipment;
        this.quantity = quantity;
    }
}