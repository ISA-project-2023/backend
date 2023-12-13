package ftn.isa.service;

import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.PickUpAppointment;
import ftn.isa.domain.Reservation;
import ftn.isa.repository.IPickUpAppointmentRepository;
import ftn.isa.domain.Company;
import ftn.isa.domain.Customer;
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
}
