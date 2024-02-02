package ftn.isa.dto;

import ftn.isa.domain.Company;
import ftn.isa.domain.Contract;
import ftn.isa.domain.Equipment;

import java.time.LocalDateTime;

public class ContractDTO {
    private Integer id;
    private String hospital;
    private String hospitalAddress;
    private String equipment;
    private String company;
    private LocalDateTime date;
    private boolean valid;
    private Integer amount;

    public ContractDTO(Integer id, String hospital, String hospitalAddress, String equipment, String company, LocalDateTime date, boolean valid, Integer amount) {
        this.id = id;
        this.hospital = hospital;
        this.hospitalAddress = hospitalAddress;
        this.equipment = equipment;
        this.company = company;
        this.date = date;
        this.valid = valid;
        this.amount = amount;
    }
    public ContractDTO(){}
    public ContractDTO(Contract contract){
        this(contract.getId(), contract.getHospital(), contract.getHospitalAddress(), contract.getEquipment().getName(), contract.getCompany().getName(), contract.getDate(), contract.isValid(), contract.getAmount());
    }

    public Integer getId() {
        return id;
    }

    public String getHospital() {
        return hospital;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getCompany() {
        return company;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isValid() {
        return valid;
    }

    public Integer getAmount() {
        return amount;
    }
}
