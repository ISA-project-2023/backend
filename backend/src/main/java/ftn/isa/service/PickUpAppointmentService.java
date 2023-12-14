package ftn.isa.service;

import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.PickUpAppointment;
import ftn.isa.repository.IPickUpAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class PickUpAppointmentService {
    @Autowired
    private IPickUpAppointmentRepository pickUpAppointmentRepository;
    public PickUpAppointment findOne(Integer id) { return pickUpAppointmentRepository.findById(id).orElseGet(null); }
    public List<PickUpAppointment> findAll() {
        return pickUpAppointmentRepository.findAll();
    }
    public Page<PickUpAppointment> findAll(Pageable page) {
        return pickUpAppointmentRepository.findAll(page);
    }
    public PickUpAppointment save(PickUpAppointment pickUpAppointment) { return pickUpAppointmentRepository.save(pickUpAppointment); }
    public void remove(Integer id) {
        pickUpAppointmentRepository.deleteById(id);
    }
    public List<PickUpAppointment> findByCompanyAdmin(CompanyAdmin companyAdmin){ return pickUpAppointmentRepository.findAllByCompanyAdmin(companyAdmin); }

    public List<PickUpAppointment> findAllByCompanyAdminOnSameDay(CompanyAdmin companyAdmin, LocalDateTime date) {
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), companyAdmin.getCompany().getStartTime().getHour(), companyAdmin.getCompany().getStartTime().getMinute(), companyAdmin.getCompany().getStartTime().getSecond());
        LocalDateTime endDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), companyAdmin.getCompany().getEndTime().getHour(), companyAdmin.getCompany().getEndTime().getMinute(), companyAdmin.getCompany().getEndTime().getSecond());

        return pickUpAppointmentRepository.findAllByCompanyAdminOnSameDay(companyAdmin, startDate, endDate);
    }
}
