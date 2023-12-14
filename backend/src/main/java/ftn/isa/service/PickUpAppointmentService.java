package ftn.isa.service;

import ftn.isa.domain.*;
import ftn.isa.dto.CompanyAdminDTO;
import ftn.isa.dto.CompanyDTO;
import ftn.isa.dto.PickUpAppointmentDTO;
import ftn.isa.repository.ICompanyAdminRepository;
import ftn.isa.repository.ICompanyRepository;
import ftn.isa.repository.IPickUpAppointmentRepository;
import ftn.isa.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class PickUpAppointmentService {
    @Autowired
    private IPickUpAppointmentRepository pickUpAppointmentRepository;
    @Autowired
    private ICompanyAdminRepository companyAdminRepository;
    @Autowired
    private ICompanyRepository companyRepository;
    public PickUpAppointment findOne(Integer id) { return pickUpAppointmentRepository.findById(id).orElseGet(null); }
    public List<PickUpAppointment> findAll() {
        return pickUpAppointmentRepository.findAll();
    }
    public List<PickUpAppointmentDTO> findCustom(LocalDateTime date, Integer companyId){
        List<CompanyAdmin> companyAdmins1 = companyAdminRepository.findAll();
        List<CompanyAdminDTO> companyAdmins = new ArrayList<>();
        Company company = null;
        for(var ad: companyAdmins1){
            if(ad.getCompany().getId().equals(companyId)){
                companyAdmins.add(new CompanyAdminDTO(ad));
                company = ad.getCompany();
            }
        }
        List<PickUpAppointmentDTO> possibleAppointments = new ArrayList<>();
        if(company==null){
            return null;
        }
        for(Integer i = company.getStartTime().getHour(); i<=company.getEndTime().getHour()-2; i+=2){
            for(var admin: companyAdmins){
                List<PickUpAppointment> appointments = pickUpAppointmentRepository.findAllByCompanyAdminId(admin.getId());
                if(!conflict(appointments,date,i)){
                    PickUpAppointment newApp = new PickUpAppointment();
                    newApp.setFree(true);
                    newApp.setDate(date.plusHours(i));
                    newApp.setDuration(2);
                    newApp.setCompanyAdmin(new CompanyAdmin(admin.getId(), admin.getUsername(), "", admin.getEmail(), admin.getPenaltyPoints(), admin.getRole(), admin.getFirstName(),"", admin.getLastName(), admin.getCategory(), 0, admin.getJobDescription(), company));
                    possibleAppointments.add(new PickUpAppointmentDTO(newApp));
                }
            }
        }
        return possibleAppointments;
    }
    private boolean conflict(List<PickUpAppointment> app, LocalDateTime date, Integer i){
        for(var pa : app){
            if(pa.getDate().isAfter(date.plusHours(i+2))||pa.getDate().plusHours(pa.getDuration()).isBefore(date.plusHours(i))){
                continue;
            }
            else{
                return true;
            }
        }
        return false;
    }
    public Page<PickUpAppointment> findAll(Pageable page) {
        return pickUpAppointmentRepository.findAll(page);
    }
    public PickUpAppointment save(PickUpAppointment pickUpAppointment) { return pickUpAppointmentRepository.save(pickUpAppointment); }
    public void remove(Integer id) {
        pickUpAppointmentRepository.deleteById(id);
    }
    public List<PickUpAppointment> findByCompanyAdminId(Integer companyAdmin){ return pickUpAppointmentRepository.findAllByCompanyAdminId(companyAdmin); }
}
