package ftn.isa.controller;

import ftn.isa.domain.*;
import ftn.isa.dto.CompanyAdminDTO;
import ftn.isa.dto.PickUpAppointmentDTO;
import ftn.isa.dto.ReservationDTO;
import ftn.isa.service.CompanyAdminService;
import ftn.isa.service.EmailService;
import ftn.isa.service.PickUpAppointmentService;
import ftn.isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private EmailService emailService;

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

        String to = reservationDto.getCustomer().getEmail();
        String subject = "Reservation Confirmation";
        String body = generateEmailBody(reservationDto);

        // Reservation details in the QR code
        String qrCodeText = generateQRCodeText(reservationDto);

        try {
            emailService.sendWithQRCode(to, body, subject, qrCodeText);
            System.out.println("Reservation information email sent successfully!");
            reservation = service.save(reservation);
        } catch (MessagingException e) {
            System.out.println("Exception sending email: " + e.toString());
            return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.CREATED);
    }

    private String generateEmailBody(ReservationDTO reservationDto) {
        String customerName = getCustomerName(reservationDto.getCustomer());

        StringBuilder mail = new StringBuilder("<html>"
                + "<body>"
                + "<p>Dear " + customerName + ",</p>"
                + "<p>Thank you for making a reservation in our service. Here are the details:</p>"
                + "<ul>"
                + "<li><strong>Company:</strong> " + reservationDto.getCompany().getName() + "</li>"
                + "<li><strong>Equipment:</strong> <br/>");

        for (Equipment e : reservationDto.getEquipment()) {
            mail.append(e.getName()).append(" (").append(e.getDescription()).append(")").append("<br/>");
        }

        mail.append("</li>")
                .append("<li><strong>Pick-up Date:</strong> ").append(reservationDto.getPickUpAppointment().getDate().toString().replace("T", " ")).append("</li>")
                .append("<li><strong>Pick-up duration:</strong> ").append(reservationDto.getPickUpAppointment().getDuration()).append("</li>")
                .append("</ul>")
                .append("<p>Please find attached QR code for more details.</p>")
                .append("<p>Best regards,<br/>The Isa Project Team</p>")
                .append("</body>")
                .append("</html>");

        return mail.toString();
    }

    private String getCustomerName(User customer) {
        if (customer instanceof Customer) {
            Customer customerObj = (Customer) customer;
            return customerObj.getFirstName() + " " + customerObj.getLastName();
        } else {
            return "Valued Customer";
        }
    }

    private String generateQRCodeText(ReservationDTO reservationDto) {
        String customerName = getCustomerName(reservationDto.getCustomer());
        return "Reservation Details:\n"
                + "Company: " + reservationDto.getCompany().getName() + "\n"
                + "Customer: " + customerName + "\n"
                + "Equipment: " + getEquipmentDetails(reservationDto.getEquipment()) + "\n"
                + "Pick-up Date: " + reservationDto.getPickUpAppointment().getDate() + "\n";
    }

    private String getEquipmentDetails(List<Equipment> equipmentList) {
        StringBuilder equipmentDetails = new StringBuilder();
        for (Equipment e : equipmentList) {
            equipmentDetails.append(e.getName()).append(" (").append(e.getDescription()).append("), ");
        }
        if (equipmentDetails.length() > 0) {
            equipmentDetails.setLength(equipmentDetails.length() - 2);
        }
        return equipmentDetails.toString();
    }


    @GetMapping(value = "/findByCompanyAdmin/{id}")
    public ResponseEntity<List<ReservationDTO>> getAppointmentsByCompanyAdmins(@PathVariable Integer id) {
        List<Reservation> reservations = service.findByCompanyAdminId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation r : reservations) {

            reservationsDTO.add(new ReservationDTO(r));
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }
}
