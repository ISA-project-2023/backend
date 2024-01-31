package ftn.isa.service;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.PickUpAppointment;
import ftn.isa.domain.Reservation;
import ftn.isa.dto.CompanyAdminDTO;
import ftn.isa.dto.PickUpAppointmentDTO;
import ftn.isa.repository.ICompanyAdminRepository;
import ftn.isa.repository.ICompanyRepository;
import ftn.isa.repository.IPickUpAppointmentRepository;
import ftn.isa.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private IReservationRepository reservationRepository;
    public PickUpAppointment findOne(Integer id) { return pickUpAppointmentRepository.findById(id).orElseGet(null); }
    public List<PickUpAppointment> findAll() {
        return pickUpAppointmentRepository.findAll();
    }
    @Transactional
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
                    newApp.setCompanyAdmin(new CompanyAdmin(admin.getId(), admin.getUsername(), "", admin.getEmail(), admin.getPenaltyPoints(), admin.getRole(), admin.getFirstName(),"", admin.getLastName(), admin.getCategory(), 0, admin.getJobDescription(), company, admin.isVerified(), admin.getPenaltyMonth()));
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
    @Transactional
    public PickUpAppointment save(PickUpAppointment pickUpAppointment) {
        boolean ok = isAdminAvailable(pickUpAppointment);
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e){
//            // Handle the exception appropriately
//        }
        if (ok) {
            return pickUpAppointmentRepository.save(pickUpAppointment);
        }
        return null;
    }
    private boolean isAdminAvailable(PickUpAppointment pua) {
        List<PickUpAppointment> pickups = pickUpAppointmentRepository.findAllByCompanyAdmin(pua.getCompanyAdmin());
        if(pua.getId() != null){
            for(PickUpAppointment p: pickups){
                if(pua.getId() == p.getId()){
                    if(!p.isFree()){
                        return false;
                    }
                    else{
                        return true;
                    }
                }
            }
        }
        for(PickUpAppointment dto: pickups){
            if(!(pua.getDate().isAfter(dto.getDate().plusHours(dto.getDuration()))
                    ||  pua.getDate().plusHours(pua.getDuration()).isBefore(dto.getDate()))){
                return false;
            }
        }
        return true;
    }

    public PickUpAppointment cancel(int pickUpAppointmentId) {
        PickUpAppointment app = findOne(pickUpAppointmentId);
        app.setFree(true);
        return save(app);
    }
    public void remove(Integer id) {
        pickUpAppointmentRepository.deleteById(id);
    }

    public List<PickUpAppointment> findAllByCompanyAdmin(CompanyAdmin companyAdmin) {
        return pickUpAppointmentRepository.findAllByCompanyAdmin(companyAdmin);
    }
    public List<PickUpAppointment> findAllByCompanyAdminOnSameDay(CompanyAdmin companyAdmin, LocalDateTime date) {
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), companyAdmin.getCompany().getStartTime().getHour(), companyAdmin.getCompany().getStartTime().getMinute(), companyAdmin.getCompany().getStartTime().getSecond());
        LocalDateTime endDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), companyAdmin.getCompany().getEndTime().getHour(), companyAdmin.getCompany().getEndTime().getMinute(), companyAdmin.getCompany().getEndTime().getSecond());

        return pickUpAppointmentRepository.findAllByCompanyAdminOnSameDay(companyAdmin, startDate, endDate);
    }
}
