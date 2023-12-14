package ftn.isa.controller;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.PickUpAppointment;
import ftn.isa.dto.PickUpAppointmentDTO;
import ftn.isa.service.CompanyAdminService;
import ftn.isa.service.PickUpAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/appointments")
public class PickUpAppointmentController {
    @Autowired
    private PickUpAppointmentService service;
    @Autowired
    private CompanyAdminService companyAdminService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<PickUpAppointmentDTO>> getAllPickUpAppointments() {
        List<PickUpAppointment> appointments = service.findAll();
        List<PickUpAppointmentDTO> PickUpAppointmentsDTO = new ArrayList<>();
        for (PickUpAppointment s : appointments) {
            PickUpAppointmentsDTO.add(new PickUpAppointmentDTO(s));
        }
        return new ResponseEntity<>(PickUpAppointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value="/custom/{day}/{month}/{year}/{companyId}")
    public ResponseEntity<List<PickUpAppointmentDTO>> getCustomOnDate(@PathVariable Integer day, @PathVariable Integer month, @PathVariable Integer year, @PathVariable Integer companyId){
        LocalDate localDate = LocalDate.of(year, month, day);
        return new ResponseEntity<>(service.findCustom(localDate.atStartOfDay(), companyId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PickUpAppointmentDTO>> getPickUpAppointmentsPage(Pageable page) {
        List<PickUpAppointment> appointments = service.findAll(page).toList();
        List<PickUpAppointmentDTO> pickUpAppointmentsDTO = new ArrayList<>();
        for (PickUpAppointment s : appointments) {
            pickUpAppointmentsDTO.add(new PickUpAppointmentDTO(s));
        }
        return new ResponseEntity<>(pickUpAppointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PickUpAppointmentDTO> getPickUpAppointment(@PathVariable Integer id) {
        PickUpAppointment appointment = service.findOne(id);
        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new PickUpAppointmentDTO(appointment), HttpStatus.OK);
    }

    @PostMapping(value = "/addNew")
    public ResponseEntity<?> savePickUpAppointment(@RequestBody PickUpAppointmentDTO appointmentDTO) {
        // TODO - check if appointment is free for that CompanyAdmin
        if (IsCompanyAdminFree(appointmentDTO)){
            // CompanyAdmin is free in requested appointment
            PickUpAppointment appointment = new PickUpAppointment();

            appointment.setDate(appointmentDTO.getDate());
            appointment.setDuration(appointmentDTO.getDuration());
            appointment.setFree(true);
            appointment.setCompanyAdmin(appointmentDTO.getCompanyAdmin());
            if (IsWithinWorkHours(appointment)){
                appointment = service.save(appointment);
                if (appointment != null){
                    return new ResponseEntity<>(new PickUpAppointmentDTO(appointment), HttpStatus.CREATED);
                }
            } else { // Invalid date, not within working hours
                return new ResponseEntity<>("Invalid date. Appointments must be within working hours.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else { // CompanyAdmin has prior appointment, cant make another appointment at the same time
            return new ResponseEntity<>("CompanyAdmin has a prior appointment at the same time.", HttpStatus.NOT_FOUND);
        }
    }
    // check if appointment is free for that CompanyAdmin
    private boolean IsCompanyAdminFree(PickUpAppointmentDTO appointmentDTO){
        LocalDateTime startAppointmentTime = appointmentDTO.getDate();
        LocalDateTime endAppointmentTime = appointmentDTO.getDate().plusHours(appointmentDTO.getDuration());

        List<PickUpAppointment> appointments = service.findAllByCompanyAdminOnSameDay(appointmentDTO.getCompanyAdmin(), startAppointmentTime);

        for (PickUpAppointment a : appointments) {
            LocalDateTime start = a.getDate();
            LocalDateTime end = a.getDate().plusHours(a.getDuration());

            if (start.isAfter(startAppointmentTime) && start.isBefore(endAppointmentTime)) {
                return false;
            }
            if (end.isAfter(startAppointmentTime) && end.isBefore(endAppointmentTime)) {
                return false;
            }
        }
        return true;
    }
    private boolean IsWithinWorkHours(PickUpAppointment appointment){
        LocalDateTime appointmentStartDate = appointment.getDate();
        LocalDateTime appointmentEndDate = appointment.getDate().plusHours(appointment.getDuration());
        LocalTime startTime = appointment.getCompanyAdmin().getCompany().getStartTime();
        LocalTime endTime = appointment.getCompanyAdmin().getCompany().getEndTime();

        return !appointmentStartDate.toLocalTime().isBefore(startTime) && !appointmentEndDate.toLocalTime().isAfter(endTime);
    }
    @PutMapping(consumes = "application/json")
    public ResponseEntity<PickUpAppointmentDTO> updatePickUpAppointment(@RequestBody PickUpAppointmentDTO appointmentDTO, HttpServletRequest request) {
        PickUpAppointment appointment = service.findOne(appointmentDTO.getId());
        if (appointment == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        appointment.setDate(appointmentDTO.getDate());
        appointment.setDuration(appointmentDTO.getDuration());
        appointment.setFree(appointmentDTO.isFree());
        appointment.setCompanyAdmin(appointmentDTO.getCompanyAdmin());

        appointment = service.save(appointment);
        return new ResponseEntity<>(new PickUpAppointmentDTO(appointment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePickUpAppointment(@PathVariable Integer id) {
        PickUpAppointment appointment = service.findOne(id);

        if (appointment != null) {
            service.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<PickUpAppointment> filterOutOldDates(List<PickUpAppointment> appointments) {
        List<PickUpAppointment> filtered = new ArrayList<>();
        for (PickUpAppointment a : appointments) {
            if (a.getDate().isAfter(LocalDateTime.now()) ){
                filtered.add(a);
            }
        }
        return filtered;
    }
    @PostMapping(value = "/findByCompanyAdmin")
    public ResponseEntity<List<PickUpAppointmentDTO>> getAppointmentsByCompanyAdmins(@RequestBody CompanyAdmin companyAdmin) {
        List<PickUpAppointment> appointments = service.findByCompanyAdminId(companyAdmin.getId());
        List<PickUpAppointment> filtered = filterOutOldDates(appointments);

        List<PickUpAppointmentDTO> pickUpAppointmentsDTO = new ArrayList<>();
        for (PickUpAppointment s : filtered) {
            pickUpAppointmentsDTO.add(new PickUpAppointmentDTO(s));
        }
        return new ResponseEntity<>(pickUpAppointmentsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/findByCompany")
    public ResponseEntity<List<PickUpAppointmentDTO>> getAppointmentsByCompany(@RequestBody Company company) {
        List<PickUpAppointment> appointments = new ArrayList<>();
        List<CompanyAdmin> admins = companyAdminService.findAllByCompany(company);
        for (CompanyAdmin ca : admins) {
            List<PickUpAppointment> adminsAppointment = service.findByCompanyAdminId(ca.getId());
            appointments.addAll(adminsAppointment);
        }
        List<PickUpAppointment> filtered = filterOutOldDates(appointments);
        List<PickUpAppointmentDTO> pickUpAppointmentsDTO = new ArrayList<>();
        for (PickUpAppointment s : filtered) {
            pickUpAppointmentsDTO.add(new PickUpAppointmentDTO(s));
        }
        return new ResponseEntity<>(pickUpAppointmentsDTO, HttpStatus.OK);
    }
}
