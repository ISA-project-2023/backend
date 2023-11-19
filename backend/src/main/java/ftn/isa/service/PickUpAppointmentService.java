package ftn.isa.service;

import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.PickUpAppointment;
import ftn.isa.repository.IPickUpAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
