package ftn.isa.controller;

import ftn.isa.domain.User;
import ftn.isa.dto.UserDTO;
import ftn.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    private UserService userService;

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> saveUser(@RequestBody Map<String, Object> requestBody) {

        Map<String, Object> userDTOMap = (Map<String, Object>) requestBody.get("userDTO");
        UserDTO userDTO = new UserDTO((Integer) userDTOMap.get("id"),(String) userDTOMap.get("username"),(String) userDTOMap.get("email"),(Integer) userDTOMap.get("penaltyPoints"),(String) userDTOMap.get("category"),(String) userDTOMap.get("firstName"),(String) userDTOMap.get("lastName"));

        String password = (String) requestBody.get("password");

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(password);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setCategory(userDTO.getCategory());
        user.setEmail(userDTO.getEmail());
        user.setPenaltyPoints(0);

        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }



    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        User user = userService.findOne(userDTO.getId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setPenaltyPoints(userDTO.getPenaltyPoints());
        user.setCategory(userDTO.getCategory());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
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
