package ftn.isa.dto;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.SystemAdmin;

public class SystemAdminDTO extends UserDTO {
    private boolean isActivated;

    public SystemAdminDTO() {}
    public SystemAdminDTO(SystemAdmin systemAdmin){

        super(systemAdmin.getId(), systemAdmin.getUsername(), systemAdmin.getEmail(), systemAdmin.getPenaltyPoints(), systemAdmin.getRole(),
                systemAdmin.getFirstName(), systemAdmin.getLastName(), systemAdmin.getCategory(), systemAdmin.getPenaltyMonth());
        this.isActivated = systemAdmin.getIsActivated();
    }

    public boolean getIsActivated() {
        return isActivated;
    }
}
