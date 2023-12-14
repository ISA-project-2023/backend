package ftn.isa.controller;

import ftn.isa.domain.Customer;
import ftn.isa.dto.CustomerDTO;
import ftn.isa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/customers")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> getOne(@PathVariable Integer id) {
        Customer customer = service.find(id);
        if(customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CustomerDTO(customer), HttpStatus.OK);
    }
}
