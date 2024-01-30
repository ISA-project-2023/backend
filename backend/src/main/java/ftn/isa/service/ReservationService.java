package ftn.isa.service;

import ftn.isa.domain.*;
import ftn.isa.dto.EquipmentAmountDTO;
import ftn.isa.dto.ReservationDTO;
import ftn.isa.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private IReservationRepository reservationRepository;
    @Autowired
    private CompanyEquipmentService companyEquipmentService;
    @Autowired
    private PickUpAppointmentService pickupService;
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_READ)
    public Reservation save(Reservation reservation, ReservationDTO reservationDto){
        PickUpAppointment pua = reservationDto.getPickUpAppointment();
        boolean ok = true;

        if(reservation.getId() == null){
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e){
//                // Handle the exception appropriately
//            }

            List<CompanyEquipment> c = companyEquipmentService.findAllByCompany(reservation.getCompany());
            List<CompanyEquipment> c1 = new ArrayList<>();

            ok = isAdminAvailable(pua);
            pua.setFree(false);

            if (!checkEquipmentAvailability(reservation.getEquipment(), c)) {
                // Not enough equipment available for the reservation
                return null;
            }

            reservation.setPickUpAppointment(pua);

            for(CompanyEquipment ce: c){
                for(EquipmentAmountDTO eq: reservation.getEquipment()){
                    if(eq.getEquipment().getId() == ce.getEquipment().getId()){
                        ce.setQuantity(ce.getQuantity() - eq.getQuantity());
                        c1.add(ce);
                    }
                }
            }

            if(ok){
                for(CompanyEquipment ce: c1){
                    companyEquipmentService.save(ce);
                }
                pickupService.save(pua);
            } else {
                // Handle the case where admin is not available
                return null;
            }
        }

        return reservationRepository.save(reservation);
    }

    private boolean checkEquipmentAvailability(List<EquipmentAmountDTO> equipment, List<CompanyEquipment> availableEquipment) {
        for (EquipmentAmountDTO eq : equipment) {
            for (CompanyEquipment ce : availableEquipment) {
                if (eq.getEquipment().getId() == ce.getEquipment().getId()) {
                    if (ce.getQuantity() < eq.getQuantity()) {
                        // Not enough equipment available
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isAdminAvailable(PickUpAppointment pua) {
        List<PickUpAppointment> pickups = pickupService.findAllByCompanyAdmin(pua.getCompanyAdmin());
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
            return save(r, new ReservationDTO(r));
        }
        return null;
    }

    @Transactional
    public Reservation cancel(Integer id) {
        Reservation r = getOne(id);
        ReservationDTO res = new ReservationDTO(r);
        if (r != null) {
            List<CompanyEquipment> c = companyEquipmentService.findAllByCompany(r.getCompany());
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
                companyEquipmentService.save(ce);
            }
            r.setStatus(ReservationStatus.CANCELED);
            return save(r, new ReservationDTO(r));
        }
        return null;
    }

    public Reservation expired(Integer id) {
        Reservation r = getOne(id);
        if (r != null) {
            r.setStatus(ReservationStatus.EXPIRED);
            return save(r,new ReservationDTO(r));
        }
        return null;
    }
}
