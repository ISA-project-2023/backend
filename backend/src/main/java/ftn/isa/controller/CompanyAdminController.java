package ftn.isa.controller;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.User;
import ftn.isa.dto.CompanyAdminDTO;
import ftn.isa.service.CompanyAdminService;
import ftn.isa.service.EmailService;
import ftn.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/companyAdmins")
public class CompanyAdminController {
    @Autowired
    private CompanyAdminService companyAdminService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private HttpSession session;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyAdminDTO>> getAllCompanyAdmins() {
        List<CompanyAdmin> companyAdmins = companyAdminService.findAll();
        List<CompanyAdminDTO> companyAdminsDTO = new ArrayList<>();
        for (CompanyAdmin s : companyAdmins) {
            companyAdminsDTO.add(new CompanyAdminDTO(s));
        }
        return new ResponseEntity<>(companyAdminsDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CompanyAdminDTO>> getCompanyAdminsPage(Pageable page) {
        List<CompanyAdmin> companyAdmins = companyAdminService.findAll(page).toList();
        List<CompanyAdminDTO> companyAdminsDTO = new ArrayList<>();
        for (CompanyAdmin s : companyAdmins) {
            companyAdminsDTO.add(new CompanyAdminDTO(s));
        }
        return new ResponseEntity<>(companyAdminsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyAdminDTO> getCompanyAdmin(@PathVariable Integer id) {

        CompanyAdmin admin = companyAdminService.findOne(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CompanyAdminDTO(admin), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json",value = "/{password}")
    public ResponseEntity<CompanyAdminDTO> saveCompanyAdmin(@RequestBody CompanyAdminDTO adminDTO, @PathVariable String password)  throws MessagingException {

        CompanyAdmin admin = new CompanyAdmin();

        admin.setId(null);
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(password);
        admin.setEmail(adminDTO.getEmail());
        admin.setPenaltyPoints(adminDTO.getPenaltyPoints());
        admin.setRole(adminDTO.getRole());
        admin.setCategory(adminDTO.getCategory());
        admin.setEnabled(false);

        String token = UUID.randomUUID().toString();
        admin.setToken(token);

        admin.setJobDescription(adminDTO.getJobDescription());
        admin.setCompany(adminDTO.getCompany());
        admin.setVerified(false);

        emailService.send(admin.getEmail(), generateNewCompanyAdminEmailBody(admin, token), "ISA Project - New Company Admin User");
        admin = companyAdminService.save(admin);
        if (admin == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CompanyAdminDTO(admin), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<CompanyAdminDTO> updateCompanyAdmin(@RequestBody CompanyAdminDTO companyAdminDTO, HttpServletRequest request) {
        CompanyAdmin admin = companyAdminService.findOne(companyAdminDTO.getId());
        if (admin == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        admin.setJobDescription(companyAdminDTO.getJobDescription());
        admin.setCompany(companyAdminDTO.getCompany());
        admin.setLastName(companyAdminDTO.getLastName());
        admin.setFirstName(companyAdminDTO.getFirstName());

        admin = companyAdminService.save(admin);
        session.setAttribute("companyAdmin", admin);
        return new ResponseEntity<>(new CompanyAdminDTO(admin), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCompanyAdmin(@PathVariable Integer id) {
        CompanyAdmin admin = companyAdminService.findOne(id);

        if (admin != null) {
            companyAdminService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/findByCompany")
    public ResponseEntity<List<CompanyAdminDTO>> getCompanyAdminsByCompany(@RequestBody Company company) {
        List<CompanyAdmin> admins = companyAdminService.findAllByCompany(company);

        List<CompanyAdminDTO> companyAdminsDTO = new ArrayList<>();
        for (CompanyAdmin s : admins) {
            companyAdminsDTO.add(new CompanyAdminDTO(s));
        }
        return new ResponseEntity<>(companyAdminsDTO, HttpStatus.OK);
    }

    @PostMapping(value="/add-existing")
    public ResponseEntity<CompanyAdminDTO> addExistingCompanyAdmin(@RequestBody CompanyAdminDTO adminDTO) throws MessagingException{
        CompanyAdmin admin = companyAdminService.findOne(adminDTO.getId());
        System.out.println(adminDTO.getUsername());
        admin.setId(adminDTO.getId());
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(admin.getPassword());
        admin.setEmail(adminDTO.getEmail());
        admin.setPenaltyPoints(adminDTO.getPenaltyPoints());
        admin.setRole(adminDTO.getRole());
        admin.setCategory(adminDTO.getCategory());
        admin.setEnabled(false);

        String token = UUID.randomUUID().toString();
        admin.setToken(token);

        admin.setJobDescription(adminDTO.getJobDescription());
        admin.setCompany(adminDTO.getCompany());
        admin.setVerified(false);

        emailService.send(admin.getEmail(), generateNewCompanyAdminEmailBody(admin, token), "ISA Project - New Company Admin User");
        admin = companyAdminService.save(admin);
        if (admin == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CompanyAdminDTO(admin), HttpStatus.CREATED);
    }

    private String generateNewCompanyAdminEmailBody(CompanyAdmin admin, String activationLink) {
        String userName = admin.getFirstName() + " " + admin.getLastName();
        String fullActivationLink = "http://localhost:4200/activate/" + activationLink;

        return  "<p>Dear <strong>" + userName + "</strong>,</p>\n" +
                "<p>Thank you for choosing our service! We're excited to have you on board.</p>\n" +
                "<p>Your registration is almost complete. Please click the following link to activate your account:</p>\n" +
                "<p><a href=\"" + fullActivationLink + "\">Activation Link</a></p>\n\n" +
                "<p>Your username is: <strong>" + admin.getUsername() + "</strong>  and your password is: <strong>" + admin.getPassword() + "</strong></p>\n\n" +
                "<p>This is a password that system admin provided so please change it as soon as possible!</p>\n" +
                "<p>If you have any questions, feel free to contact our support team.</p>\n\n" +
                "<p>Best regards,<br/>ISA project members</p>";
    }
}
