package ftn.isa.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CompanyEquipmentId implements Serializable {

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "equipment_id")
    private Integer equipmentId;

    public CompanyEquipmentId(){}
    public CompanyEquipmentId(Integer companyId, Integer equipmentId) {
        this.companyId = companyId;
        this.equipmentId = equipmentId;
    }

    public Integer getCompanyId() { return companyId; }
    public Integer getEquipmentId() { return equipmentId; }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}