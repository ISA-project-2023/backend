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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<PickUpAppointmentDTO> savePickUpAppointment(@RequestBody Map<String, Object> requestBody) {
//
//        Map<String, Object> pickUpAppointmentDTOMap = (Map<String, Object>) requestBody.get("userDTO");
//        PickUpAppointmentDTO appointmentDTO = new PickUpAppointmentDTO((Integer) pickUpAppointmentDTOMap.get("id"),(String) pickUpAppointmentDTOMap.get("jobDescription") /*COMPANY*/);
//
//        PickUpAppointment appointment = new PickUpAppointment();
//        appointment.setDate(appointmentDTO.getDate());
//        appointment.setDuration(appointmentDTO.getDuration());
//        appointment.setFree(appointmentDTO.isFree());
//        appointment.setCompanyAdmin(appointmentDTO.getCompanyAdmin());
//
//        appointment = service.save(appointment);
//        return new ResponseEntity<>(new PickUpAppointmentDTO(appointment), HttpStatus.CREATED);
//    }
    //@PostMapping(consumes = "application/json")
    @PostMapping(value = "/addNew")
    //public ResponseEntity<?> savePickUpAppointment(@RequestBody PickUpAppointmentDTO appointmentDTO, @RequestParam String dateString) {
    public ResponseEntity<PickUpAppointmentDTO> savePickUpAppointment(@RequestBody PickUpAppointmentDTO appointmentDTO) {
        // TODO - check if appointment is free for that CompanyAdmin
        if (IsCompanyAdminFree(appointmentDTO)){
            // CompanyAdmin is free in requested appointment
            PickUpAppointment appointment = new PickUpAppointment();

            appointment.setDate(appointmentDTO.getDate());
            appointment.setDuration(appointmentDTO.getDuration());
            appointment.setFree(appointmentDTO.isFree());
            appointment.setCompanyAdmin(appointmentDTO.getCompanyAdmin());

            appointment = service.save(appointment);
            if (appointment != null){
                //return new ResponseEntity<>(HttpStatus.CREATED);
                return new ResponseEntity<>(new PickUpAppointmentDTO(appointment), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            // CompanyAdmin has prior appointment, cant make another apointment at the same time
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // TODO - check if appointment is free for that CompanyAdmin
    private boolean IsCompanyAdminFree(PickUpAppointmentDTO appointmentDTO){
        return true;
    }
    private LocalDateTime formatDate(String datesString) {
        //String dateString = "2023-11-19T12:30:45";
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dateString = "19-11-2023 12:30:45";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        return localDateTime;
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

    @PostMapping(value = "/findByCompanyAdmin")
    public ResponseEntity<List<PickUpAppointmentDTO>> getAppointmentsByCompanyAdmins(@RequestBody CompanyAdmin companyAdmin) {
        List<PickUpAppointment> appointments = service.findByCompanyAdmin(companyAdmin);

        List<PickUpAppointmentDTO> pickUpAppointmentsDTO = new ArrayList<>();
        for (PickUpAppointment s : appointments) {
            pickUpAppointmentsDTO.add(new PickUpAppointmentDTO(s));
        }
        return new ResponseEntity<>(pickUpAppointmentsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/findByCompany")
    public ResponseEntity<List<PickUpAppointmentDTO>> getAppointmentsByCompany(@RequestBody Company company) {
        List<PickUpAppointment> appointments = new ArrayList<>();
        List<CompanyAdmin> admins = companyAdminService.findAllByCompany(company);
        for (CompanyAdmin ca : admins) {
            List<PickUpAppointment> adminsAppointment = service.findByCompanyAdmin(ca);
            appointments.addAll(adminsAppointment);
        }

        List<PickUpAppointmentDTO> pickUpAppointmentsDTO = new ArrayList<>();
        for (PickUpAppointment s : appointments) {
            pickUpAppointmentsDTO.add(new PickUpAppointmentDTO(s));
        }
        return new ResponseEntity<>(pickUpAppointmentsDTO, HttpStatus.OK);
    }
}