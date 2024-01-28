package ftn.isa.service;

import ftn.isa.domain.*;
import ftn.isa.dto.EquipmentAmountDTO;
import ftn.isa.dto.ReservationDTO;
import ftn.isa.repository.ICompanyEquipmentRepository;
import ftn.isa.repository.IPickUpAppointmentRepository;
import ftn.isa.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private IReservationRepository reservationRepository;
    @Autowired
    private ICompanyEquipmentRepository companyEquipmentRepository;
    public Reservation save(Reservation reservation){
        if(reservation.getId()==null){
            List<CompanyEquipment> c = companyEquipmentRepository.findAllByCompany(reservation.getCompany());
            List<CompanyEquipment> c1 = new ArrayList<>();
            for(CompanyEquipment ce: c){
                for(EquipmentAmountDTO eq: reservation.getEquipment()){
                    if(eq.getEquipment().getId()==ce.getEquipment().getId()){
                        ce.setQuantity(ce.getQuantity()-eq.getQuantity());
                        if(ce.getQuantity()<0){
                            return null;
                        }
                        c1.add(ce);
                    }
                }
            }
            for(CompanyEquipment ce:c1){
                companyEquipmentRepository.save(ce);
            }
        }
        return reservationRepository.save(reservation);
    }
    public Reservation getOne(int id){ return reservationRepository.findById(id).orElseGet(null); }
    public List<Reservation> getAll(){
        return reservationRepository.findAll();
    }
    public List<Reservation> getAllByCustomer(Integer id){
        List<Reservation> reservations = new ArrayList<>();
        List<Reservation> result = new ArrayList<>();
        reservations = getAll();
        for (Reservation r : reservations) {
            if(r.getCustomer().getId().equals(id) && r.getPickUpAppointment().getDate().toLocalDate().isAfter(LocalDate.now())){
                result.add(r);
            }
        }
        return result;
    }
    public List<Reservation> getAllPreviousByCustomer(Integer id){
        List<Reservation> reservations = new ArrayList<>();
        List<Reservation> result = new ArrayList<>();
        reservations = getAll();
        for (Reservation r : reservations) {
            if(r.getCustomer().getId().equals(id) && r.getPickUpAppointment().getDate().toLocalDate().isBefore(LocalDate.now())){
                result.add(r);
            }
        }
        return result;
    }
    public List<Reservation> getAllByCompany(Integer id){
        return reservationRepository.findAllByCompanyId(id);
    }

    public List<Reservation> findByCompanyAdminId(Integer id){ return reservationRepository.findAllByPickUpAppointmentCompanyAdminId(id); }

    public Reservation pickUp(Integer id) {
        Reservation r = getOne(id);
        if (r != null) {
            r.setStatus(ReservationStatus.PICKED_UP);
            return save(r);
        }
        return null;
    }

    public Reservation cancel(Integer id) {
        Reservation r = getOne(id);
        ReservationDTO res = new ReservationDTO(r);
        if (r != null) {
            List<CompanyEquipment> c = companyEquipmentRepository.findAllByCompany(r.getCompany());
            List<CompanyEquipment> c1 = new ArrayList<>();
            for(CompanyEquipment ce: c){
                for(EquipmentAmountDTO eq: res.getEquipment()){
                    if(eq.getEquipment().getId()==ce.getEquipment().getId()){
                        ce.setQuantity(ce.getQuantity()+eq.getQuantity());
                        if(ce.getQuantity()<0){
                            return null;
                        }
                        c1.add(ce);
                    }
                }
            }
            for(CompanyEquipment ce:c1){
                companyEquipmentRepository.save(ce);
            }
            r.setStatus(ReservationStatus.CANCELED);
            return save(r);
        }
        return null;
    }

    public Reservation expired(Integer id) {
        Reservation r = getOne(id);
        if (r != null) {
            r.setStatus(ReservationStatus.EXPIRED);
            return save(r);
        }
        return null;
    }

}
