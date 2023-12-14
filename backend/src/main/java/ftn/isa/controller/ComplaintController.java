package ftn.isa.controller;

import ftn.isa.domain.ComplaintAboutAdmin;
import ftn.isa.domain.ComplaintAboutCompany;
import ftn.isa.domain.Reservation;
import ftn.isa.dto.ComplaintAboutAdminDTO;
import ftn.isa.dto.ComplaintAboutCompanyDTO;
import ftn.isa.dto.ReservationDTO;
import ftn.isa.service.ComplaintService;
import ftn.isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping(value = "/allCompanyComplaints")
    public ResponseEntity<List<ComplaintAboutCompanyDTO>> getAllCompanyComplaints() {
        List<ComplaintAboutCompany> complaints = complaintService.findAllComplaintsAboutCompany();

        List<ComplaintAboutCompanyDTO> complaintsDTO = new ArrayList<>();
        for (ComplaintAboutCompany c : complaints) {

            complaintsDTO.add(new ComplaintAboutCompanyDTO(c));
        }

        return new ResponseEntity<>(complaintsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/allCompanyAdminComplaints")
    public ResponseEntity<List<ComplaintAboutAdminDTO>> getAllCompanyAdminComplaints() {
        List<ComplaintAboutAdmin> complaints = complaintService.findAllComplaintsAboutAdmin();

        List<ComplaintAboutAdminDTO> complaintsDTO = new ArrayList<>();
        for (ComplaintAboutAdmin c : complaints) {

            complaintsDTO.add(new ComplaintAboutAdminDTO(c));
        }

        return new ResponseEntity<>(complaintsDTO, HttpStatus.OK);
    }
}
