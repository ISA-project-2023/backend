package ftn.isa.controller;

import ftn.isa.domain.*;
import ftn.isa.dto.CompanyAdminDTO;
import ftn.isa.dto.EquipmentAmountDTO;
import ftn.isa.dto.PickUpAppointmentDTO;
import ftn.isa.dto.ReservationDTO;
import ftn.isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    @Autowired
    private PickUpAppointmentService pickUpAppointmentService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = service.getAll();
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation s : reservations) {
            reservationDTOS.add(new ReservationDTO(s));
        }
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/cancel/{id}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Integer id) {
        Reservation r = service.getOne(id);

        if (r == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PickUpAppointment appointment = pickUpAppointmentService.cancel(r.getPickUpAppointment().getId());

        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Reservation res = service.cancel(id);

        User user = userService.findOne(res.getCustomer().getId());
        int penaltyPoints = user.getPenaltyPoints();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appointmentDate = appointment.getDate();

        long hoursDifference = ChronoUnit.HOURS.between(now, appointmentDate);

        if (hoursDifference < 24) {
            penaltyPoints += 2;
        } else {
            penaltyPoints++;
        }

        user.setPenaltyPoints(penaltyPoints);
        userService.save(user);

        return new ResponseEntity<>(new ReservationDTO(res), HttpStatus.OK);
    }

    @PutMapping(value = "/markAsPicked/{id}", consumes = "application/json")
    public ResponseEntity<ReservationDTO> deliverReservation(@PathVariable Integer id, @RequestBody ReservationDTO reservationDto) {
        Reservation r = service.getOne(id);
        if (r == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Reservation res = service.pickUp(id);

        String to = r.getCustomer().getEmail();
        String subject = "Equipment successfully delivered!";
        String body = generateDeliveringConfirmationEmail(reservationDto);

        try {
            emailService.send(to, body, subject);
            System.out.println("Reservation delivering email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Exception sending email: " + e.toString());
            return new ResponseEntity<>(new ReservationDTO(res), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ReservationDTO(res), HttpStatus.OK);
    }

    @PutMapping(value = "/markAsExpired/{id}")
    public ResponseEntity<ReservationDTO> expiredReservation(@PathVariable Integer id) {
        Reservation r = service.getOne(id);
        if (r == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Reservation res = service.expired(id);
        User user = userService.findOne(res.getCustomer().getId());
        int penaltyPoints = user.getPenaltyPoints();
        penaltyPoints += 2;
        user.setPenaltyPoints(penaltyPoints);
        userService.save(user);
        return new ResponseEntity<>(new ReservationDTO(res), HttpStatus.OK);
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

    @GetMapping(value = "/allPreviousByCustomer/{id}")
    public ResponseEntity<List<ReservationDTO>> getAllPreviousByCustomer(@PathVariable Integer id) {
        List<Reservation> reservations = service.getAllPreviousByCustomer(id);
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

        for (EquipmentAmountDTO e : reservationDto.getEquipment()) {
            mail.append(e.getEquipment().getName()).append(" (").append(e.getEquipment().getDescription()).append(" × ").append(e.getQuantity()).append(")").append("<br/>");
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

    private String generateDeliveringConfirmationEmail(ReservationDTO reservation) {
        String customerName = getCustomerName(reservation.getCustomer());

        StringBuilder mail = new StringBuilder("<html>"
                + "<body>"
                + "<p>Dear " + customerName + ",</p>"
                + "<p>Thank you for your trust. Your order was succesfully delivered. Here are the details:</p>"
                + "<ul>"
                + "<li><strong>Company:</strong> " + reservation.getCompany().getName() + "</li>"
                + "<li><strong>Equipment:</strong> <br/>");

        for (EquipmentAmountDTO e : reservation.getEquipment()) {
            mail.append(e.getEquipment().getName()).append(" (").append(e.getEquipment().getDescription()).append(" × ").append(e.getQuantity()).append(")").append("<br/>");
        }

        mail.append("</li>")
                .append("<li><strong>Pick-up Date:</strong> ").append(reservation.getPickUpAppointment().getDate().toString().replace("T", " ")).append("</li>")
                .append("</ul>")
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
                + "Pick-up-Date: " + reservationDto.getPickUpAppointment().getDate() + "\n";
    }

    private String getEquipmentDetails(List<EquipmentAmountDTO> equipmentList) {
        StringBuilder equipmentDetails = new StringBuilder();
        for (EquipmentAmountDTO e : equipmentList) {
            equipmentDetails.append(e.getEquipment().getName()).append(" (").append(e.getEquipment().getDescription()).append(" × ").append(e.getQuantity()).append(")").append("), ");
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
            if (r.getStatus() == ReservationStatus.PENDING && isAppointmentExpired(r.getPickUpAppointment())) {
                Reservation expiredReservation = service.expired(r.getId());
                User user = userService.findOne(expiredReservation.getCustomer().getId());
                int penaltyPoints = user.getPenaltyPoints();
                penaltyPoints += 2;
                user.setPenaltyPoints(penaltyPoints);
                userService.save(user);
            }
            reservationsDTO.add(new ReservationDTO(r));
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    private boolean isAppointmentExpired(PickUpAppointment appointment) {
        LocalDateTime endTime = appointment.getDate().plusHours(appointment.getDuration());
        return endTime.isBefore(LocalDateTime.now());
    }
}
