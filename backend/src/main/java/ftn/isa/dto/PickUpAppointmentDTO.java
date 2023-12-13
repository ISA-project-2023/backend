package ftn.isa.dto;

import ftn.isa.domain.PickUpAppointment;
import ftn.isa.domain.CompanyAdmin;
import java.time.LocalDateTime;

public class PickUpAppointmentDTO {
    private Integer id;
    private LocalDateTime date;
    private Integer duration;
    private boolean isFree;
    private CompanyAdmin companyAdmin;

    public PickUpAppointmentDTO() {
    }
    public PickUpAppointmentDTO(Integer id, LocalDateTime date, Integer duration, boolean isFree, CompanyAdmin companyAdmin) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.isFree = isFree;
        this.companyAdmin = companyAdmin;
    }
    public PickUpAppointmentDTO(PickUpAppointment pickUpAppointment){
        this.id = pickUpAppointment.getId();
        this.date = pickUpAppointment.getDate();
        this.duration = pickUpAppointment.getDuration();
        this.isFree = pickUpAppointment.isFree();
        this.companyAdmin = pickUpAppointment.getCompanyAdmin();
    }

    public Integer getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public Integer getDuration() { return duration; }
    public boolean isFree() { return isFree; }
    public CompanyAdmin getCompanyAdmin() { return companyAdmin; }
}
