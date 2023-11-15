package ftn.isa.controller;

import ftn.isa.domain.Company;
import ftn.isa.dto.CompanyDTO;
import ftn.isa.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getCompaniesPage(Pageable page) {
        List<Company> companies = companyService.findAll(page).toList();
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company s : companies) {
            companiesDTO.add(new CompanyDTO(s));
        }
        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }
//    @GetMapping(value = "/{name}")
//    public ResponseEntity<List<CompanyDTO>> getCompaniesByName(@PathVariable String name) {
//        List<Company> companies = companyService.findByName(name);
//        List<CompanyDTO> companiesDTO = new ArrayList<>();
//        for (Company s : companies) {
//            companiesDTO.add(new CompanyDTO(s));
//        }
//        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Integer id) {
        Company c = companyService.findOne(id);
        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CompanyDTO(c), HttpStatus.OK);
    }
    @PostMapping(consumes = "aplication/json")
    public ResponseEntity<CompanyDTO> saveCompany(@RequestBody CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setLocation(companyDTO.getLocation());
        company.setGrade(companyDTO.getGrade());
        company.setStartTime(companyDTO.getStartTime());
        company.setEndTime(companyDTO.getEndTime());

        //validation for start and end time
        int comparisonLocalTime = company.getEndTime().compareTo(company.getStartTime());
        if (comparisonLocalTime < 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        company = companyService.save(company);
        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.CREATED);
    }
    @PutMapping(consumes = "aplication/json")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO companyDTO) {
        Company company = companyService.findOne(companyDTO.getId());
        if (company == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        company.setName(companyDTO.getName());
        company.setLocation(companyDTO.getLocation());
        company.setGrade(companyDTO.getGrade());
        company.setStartTime(companyDTO.getStartTime());
        company.setEndTime(companyDTO.getEndTime());

        company = companyService.save(company);
        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {
        Company company = companyService.findOne(id);
        if (company != null){
            companyService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
