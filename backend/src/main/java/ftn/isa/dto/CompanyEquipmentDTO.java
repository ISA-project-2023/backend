package ftn.isa.dto;

import ftn.isa.domain.CompanyEquipment;
public class CompanyEquipmentDTO {
    private Integer id;
    private CompanyDTO company;
    private EquipmentDTO equipment;

    public Integer getId() { return id; }
    public CompanyDTO getCompany() { return company; }
    public EquipmentDTO getEquipment() { return equipment; }

    public CompanyEquipmentDTO(CompanyEquipment companyEquipment) {
        this.id = companyEquipment.getId();
        this.company = new CompanyDTO(companyEquipment.getCompany());
        this.equipment = new EquipmentDTO(companyEquipment.getEquipment());
    }

    public CompanyEquipmentDTO(Integer id, CompanyDTO company, EquipmentDTO equipment) {
        this.id = id;
        this.company = company;
        this.equipment = equipment;
    }
}