package ftn.isa.service;

import ftn.isa.domain.*;
import ftn.isa.repository.IPickUpAppointmentRepository;
import ftn.isa.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private IReservationRepository reservationRepository;

    public Reservation save(Reservation reservation){
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
            if(r.getCustomer().getId().equals(id)){
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
        if (r != null) {
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
