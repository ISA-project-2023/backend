package ftn.isa.controller;

import ftn.isa.domain.*;
import ftn.isa.dto.CompanyAdminDTO;
import ftn.isa.dto.CustomerDTO;
import ftn.isa.dto.SystemAdminDTO;
import ftn.isa.dto.UserDTO;
import ftn.isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CompanyAdminService companyAdminService;
    @Autowired
    private SystemAdminService systemAdminService;
    @Autowired
    private  HttpSession session;
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest, HttpServletRequest request, HttpServletResponse response) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        Customer loggedinCustomer = null;
        CompanyAdmin loggedinCompanyAdmin = null;
        SystemAdmin loggedinSystemAdmin = null;
        User authenticatedUser = userService.authenticate(username, password);
        if (authenticatedUser.getRole() == UserRole.COMPANY_ADMIN){
            loggedinCompanyAdmin = companyAdminService.findOne(authenticatedUser.getId());
        } else if (authenticatedUser.getRole() == UserRole.CUSTOMER){
            loggedinCustomer = customerService.find(authenticatedUser.getId());
        } else if (authenticatedUser.getRole() == UserRole.SYSTEM_ADMIN){
            loggedinSystemAdmin =  systemAdminService.find(authenticatedUser.getId());
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        if (authenticatedUser != null && authenticatedUser.isEnabled()) {
            authenticatedUser = userService.resetPenaltyPoints(authenticatedUser);
            session = request.getSession();
            session.setAttribute("user", authenticatedUser);
            if (loggedinCustomer != null){
                session.setAttribute("customer", loggedinCustomer);
            } else if (loggedinCompanyAdmin != null){
                session.setAttribute("companyAdmin", loggedinCompanyAdmin);
            } else if (loggedinSystemAdmin != null){
                session.setAttribute("systemAdmin", loggedinSystemAdmin);
            }
            String sessionId = session.getId();

            Cookie cookie = new Cookie("JSESSIONID", sessionId);
            cookie.setPath("/");
            response.addCookie(cookie);

            return new ResponseEntity<>(sessionId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
     @GetMapping("/current-customer")
     public ResponseEntity<CustomerDTO> getCurrentCustomer(HttpServletRequest request){
        if(session!=null){
            Customer customer = (Customer) session.getAttribute("customer");
            return new ResponseEntity<>(new CustomerDTO(customer), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
    @GetMapping("/current-companyAdmin")
    public ResponseEntity<CompanyAdminDTO> getCurrentCompanyAdmin(HttpServletRequest request){
        if(session!=null){
            CompanyAdmin admin = (CompanyAdmin) session.getAttribute("companyAdmin");
            if(admin != null){
                return new ResponseEntity<>(new CompanyAdminDTO(admin), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/current-user")
    public ResponseEntity<UserDTO> getCurrentUser(HttpServletRequest request){
        if(session!=null){
            User user = (User) session.getAttribute("user");
            if(user != null){
                return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/current-systemAdmin")
    public ResponseEntity<SystemAdminDTO> getCurrentSystemAdmin(HttpServletRequest request){
        if(session!=null){
            SystemAdmin systemAdmin = (SystemAdmin) session.getAttribute("systemAdmin");
            return new ResponseEntity<>(new SystemAdminDTO(systemAdmin), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }


    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    // GET /api/students?page=0&size=5&sort=firstName,DESC
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsersPage(Pageable page) {
        List<User> users = userService.findAll(page).toList();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {

        User user = userService.findOne(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<String> activateAccount(@PathVariable String token) {
        Customer customer = customerService.findByToken(token);

        if (customer != null) {
            customer.setEnabled(true);
            customerService.save(customer);
            return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid activation token", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> saveUser(@RequestBody CustomerDTO customerDTO, @RequestParam String password) throws MessagingException {

        List<User> users = new ArrayList<>();
        users = userService.findAll();
        for(User u: users){
            if (u.getEmail().equals(customerDTO.getEmail())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        Customer customer = new Customer();

        customer.setCity(customerDTO.getCity());
        customer.setCountry(customerDTO.getCountry());
        customer.setCompanyInfo(customerDTO.getCompanyInfo());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(password);
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setRole(customerDTO.getRole());
        customer.setEmail(customerDTO.getEmail());
        customer.setPenaltyPoints(0);
        customer.setCategory(customerDTO.getCategory());

        String token = UUID.randomUUID().toString();
        customer.setToken(token);
        customer.setEnabled(false);

        emailService.send(customer.getEmail(), generateActivationEmailBody(customer.getFirstName()+" "+ customer.getLastName(), token), "ISA Project - Confirm registration");
        customer = customerService.save(customer);

        if(customer != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    public String generateActivationEmailBody(String userName, String activationLink) {
        String fullActivationLink = "http://localhost:4200/activate/" + activationLink;

        return "<p>Dear <strong>" + userName + "</strong>,</p>\n" +
                "<p>Thank you for choosing our service! We're excited to have you on board.</p>\n" +
                "<p>Your registration is almost complete. Please click the following link to activate your account:</p>\n" +
                "<p><a href=\"" + fullActivationLink + "\">Activation Link</a></p>\n" +
                "<p>If you have any questions, feel free to contact our support team.</p>\n" +
                "<p>Best regards,<br/>ISA project members</p>";
    }
    @PostMapping(value = "/saveSystemAdmin", consumes = "application/json")
    public ResponseEntity<?> saveSystemAdmin(@RequestBody SystemAdminDTO userDTO, @RequestParam String password) {

        SystemAdmin user = new SystemAdmin();

        user.setUsername(userDTO.getUsername());
        user.setPassword(password);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        user.setPenaltyPoints(0);
        user.setCategory(userDTO.getCategory());
        user.setActivated(userDTO.getIsActivated());

        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setEnabled(true);

        user = systemAdminService.save(user);

        if(user != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/store", consumes = "application/json")
    public ResponseEntity<?> storeUser(@RequestBody UserDTO userDTO, @RequestParam String password) {

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(password);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        user.setPenaltyPoints(0);
        user.setCategory(userDTO.getCategory());

        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setEnabled(false);

        user = userService.save(user);

        if(user != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value="/updateSystemAdmin",consumes = "application/json")
    public ResponseEntity<SystemAdminDTO> updateSystemAdmin(@RequestBody Integer id) {
        SystemAdmin user = systemAdminService.find(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setActivated(true);
        SystemAdmin systemAdmin = systemAdminService.save(user);
        session.setAttribute("systemAdmin", systemAdmin);
        return new ResponseEntity<>(new SystemAdminDTO(user), HttpStatus.OK);
    }

    @PutMapping(path="/customer", consumes = "application/json")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, HttpServletRequest request) {
        Customer loggedInCustomer = (session != null) ? (Customer) session.getAttribute("customer") : null;

        if (loggedInCustomer == null || !loggedInCustomer.getId().equals(customerDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Customer customer = customerService.find(customerDTO.getId());
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        customer.setPenaltyPoints(customerDTO.getPenaltyPoints());
        customer.setRole(customerDTO.getRole());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setCity(customerDTO.getCity());
        customer.setCountry(customerDTO.getCountry());
        customer.setCompanyInfo(customerDTO.getCompanyInfo());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customer = customerService.save(customer);
        session.setAttribute("customer", customer);
        return new ResponseEntity<>(new CustomerDTO(customer), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {

        User user = userService.findOne(id);

        if (user != null) {
            userService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findUsername")
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String username) {

        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/findLastName")
    public ResponseEntity<List<UserDTO>> getUsersByLastName(@RequestParam String lastName) {

        List<User> users = userService.findByLastName(lastName);

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/premium")
    public ResponseEntity<List<UserDTO>> findPremiumUsers() {

        List<User> users = userService.findPremiumUsers();

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/findFirstLast")
    public ResponseEntity<List<UserDTO>> getUsersByFirstNameAndLastName(@RequestParam String firstName,
                                                                              @RequestParam String lastName) {

        List<User> users = userService.findByFirstNameAndLastName(firstName, lastName);

        // convert students to DTOs
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User s : users) {
            usersDTO.add(new UserDTO(s));
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }
    @PutMapping(consumes = "application/json", value = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserDTO userDTO, @RequestParam String password){
        User user = userService.findOne(userDTO.getId());
        user.setPassword(password);
        user = userService.save(user);

        if (user.getRole().toString().equals("COMPANY_ADMIN")){
            CompanyAdmin companyAdmin = companyAdminService.findOne(user.getId());
            if (companyAdmin == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            companyAdmin.setVerified(true);
            companyAdmin = companyAdminService.save(companyAdmin);
        }

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }
}
