package ftn.isa.controller;

import ftn.isa.domain.PickUpAppointment;
import ftn.isa.domain.Reservation;
import ftn.isa.domain.ReservationStatus;
import ftn.isa.dto.ReservationDTO;
import ftn.isa.service.PickUpAppointmentService;
import ftn.isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService service;

    @Autowired
    private PickUpAppointmentService pickupService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = service.getAll();
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation s : reservations) {
            reservationDTOS.add(new ReservationDTO(s));
        }
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/allByCustomer/{id}")
    public ResponseEntity<List<ReservationDTO>> getAllByCustomer(@PathVariable Integer id) {
        List<Reservation> reservations = service.getAllByCustomer(id);
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation s : reservations) {
            reservationDTOS.add(new ReservationDTO(s));
        }
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/allByCompany/{id}")
    public ResponseEntity<List<ReservationDTO>> getAllByCompany(@PathVariable Integer id) {
        List<Reservation> reservations = service.getAllByCompany(id);
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation s : reservations) {
            reservationDTOS.add(new ReservationDTO(s));
        }
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservationDto) {

        Reservation reservation = new Reservation();
        reservation.setCompany(reservationDto.getCompany());
        reservation.setCustomer(reservationDto.getCustomer());
        reservation.setEquipment(reservationDto.getEquipment());
        reservation.setStatus(ReservationStatus.PENDING);
        PickUpAppointment pua = reservationDto.getPickUpAppointment();
        pua.setFree(false);
        pua = pickupService.save(pua);
        reservation.setPickUpAppointment(pua);

        reservation = service.save(reservation);
        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.CREATED);
    }
}
