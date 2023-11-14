package ftn.isa.controller;

import ftn.isa.domain.Company;
import ftn.isa.domain.User;
import ftn.isa.dto.CompanyDTO;
import ftn.isa.dto.UserDTO;
import ftn.isa.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company s : companies) {
            companiesDTO.add(new CompanyDTO(s));
        }
        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    // GET /api/students?page=0&size=5&sort=firstName,DESC
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getCompaniesPage(Pageable page) {
        List<Company> companies = companyService.findAll(page).toList();
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company s : companies) {
            companiesDTO.add(new CompanyDTO(s));
        }
        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }
}
