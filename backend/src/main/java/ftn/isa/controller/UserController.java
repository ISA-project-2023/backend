package ftn.isa.controller;

import ftn.isa.domain.Employee;
import ftn.isa.domain.User;
import ftn.isa.domain.UserRole;
import ftn.isa.dto.EmployeeDTO;
import ftn.isa.dto.UserDTO;
import ftn.isa.service.EmailService;
import ftn.isa.service.EmployeeService;
import ftn.isa.service.UserService;
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
    private EmployeeService employeeService;
    @Autowired
    private  HttpSession session;
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest, HttpServletRequest request, HttpServletResponse response) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        Employee loggedinEemployee = null;
        User authenticatedUser = userService.authenticate(username, password);
        if(authenticatedUser.getRole()== UserRole.EMPLOYEE){
            loggedinEemployee = employeeService.find(authenticatedUser.getId());
        }

        if (authenticatedUser != null && authenticatedUser.isEnabled()) {
            session = request.getSession();
            session.setAttribute("user", authenticatedUser);
            session.setAttribute("employee", loggedinEemployee);

            String sessionId = session.getId();

            Cookie cookie = new Cookie("JSESSIONID", sessionId);
            cookie.setPath("/");
            response.addCookie(cookie);

            return new ResponseEntity<>(sessionId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
     @GetMapping("/current-employee")
     public ResponseEntity<EmployeeDTO> getCurrentEmployee(HttpServletRequest request){
        if(session!=null){
            Employee employee = (Employee) session.getAttribute("employee");
            return new ResponseEntity<>(new EmployeeDTO(employee), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
    @GetMapping("/current-user")
    public ResponseEntity<UserDTO> getCurrentUser(HttpServletRequest request){
        if(session!=null){
            User user = (User) session.getAttribute("user");
            return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
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
        Employee employee = employeeService.findByToken(token);

        if (employee != null) {
            employee.setEnabled(true);
            employeeService.save(employee);
            return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid activation token", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> saveUser(@RequestBody EmployeeDTO employeeDTO, @RequestParam String password) throws MessagingException {

        Employee employee = new Employee();

        employee.setCity(employeeDTO.getCity());
        employee.setCountry(employeeDTO.getCountry());
        employee.setCompanyInfo(employeeDTO.getCompanyInfo());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(password);
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setRole(employeeDTO.getRole());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPenaltyPoints(0);
        employee.setCategory(employeeDTO.getCategory());

        String token = UUID.randomUUID().toString();
        employee.setToken(token);
        employee.setEnabled(false);

        emailService.send(employee.getEmail(), generateActivationEmailBody(employee.getFirstName()+" "+employee.getLastName(), token), "ISA Project - Confirm registration");
        employee = employeeService.save(employee);

        if(employee != null){
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

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        User loggedInUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loggedInUser == null || !loggedInUser.getId().equals(userDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = employeeService.find(userDTO.getId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPenaltyPoints(userDTO.getPenaltyPoints());
        user.setRole(userDTO.getRole());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        user = userService.save(user);
        session.setAttribute("user", user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @PutMapping(path="/employee", consumes = "application/json")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO, HttpServletRequest request) {
        Employee loggedInEmployee = (session != null) ? (Employee) session.getAttribute("employee") : null;

        if (loggedInEmployee == null || !loggedInEmployee.getId().equals(employeeDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Employee employee = employeeService.find(employeeDTO.getId());
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        employee.setPenaltyPoints(employeeDTO.getPenaltyPoints());
        employee.setRole(employeeDTO.getRole());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setCity(employeeDTO.getCity());
        employee.setCountry(employeeDTO.getCountry());
        employee.setCompanyInfo(employeeDTO.getCompanyInfo());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());

        employee = employeeService.save(employee);
        session.setAttribute("employee", employee);
        return new ResponseEntity<>(new EmployeeDTO(employee), HttpStatus.OK);
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
}
