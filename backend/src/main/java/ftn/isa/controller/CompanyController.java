package ftn.isa.controller;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyEquipment;
import ftn.isa.domain.CompanyEquipmentId;
import ftn.isa.domain.Equipment;
import ftn.isa.dto.*;
import ftn.isa.service.CompanyEquipmentService;
import ftn.isa.service.CompanyService;
import ftn.isa.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private CompanyEquipmentService companyEquipmentService;


    private CompanyDTO setCompanyDTO(Company company){
        CompanyDTO companyDTO = new CompanyDTO(company);
        List<EquipmentAmountDTO> list = new ArrayList<>();
        List<CompanyEquipment> equipmentInCompany = companyEquipmentService.findAllByCompany(company);
        for (CompanyEquipment companyEquip : equipmentInCompany) {
            EquipmentAmountDTO equipmentAmountDTO = new EquipmentAmountDTO(companyEquip.getEquipment(), companyEquip.getQuantity());
            list.add(equipmentAmountDTO);
        }
        companyDTO.setEquipmentAmountInStock(list);
        return companyDTO;
    }
    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company s : companies) {
            CompanyDTO companyDTO = setCompanyDTO(s);
            companiesDTO.add(companyDTO);
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Integer id) {
        Company c = companyService.findOne(id);
        if (c == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CompanyDTO companyDTO = setCompanyDTO(c);
        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CompanyDTO> saveCompany(@RequestBody CompanyDTO companyDTO) {
        Company company = new Company();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        company.setName(companyDTO.getName());
        company.setLocation(companyDTO.getLocation());
        company.setGrade(companyDTO.getGrade());
        company.setStartTime(LocalTime.parse(companyDTO.getStartTime(), formatter));
        company.setEndTime(LocalTime.parse(companyDTO.getEndTime(), formatter));


        //validation for start and end time
        int comparisonLocalTime = company.getEndTime().compareTo(company.getStartTime());
        if (comparisonLocalTime < 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        company = companyService.save(company);
        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.CREATED);
    }
    @PutMapping(consumes = "application/json")
    public ResponseEntity<CompanyDTO> updateCompanyInfo(@RequestBody CompanyDTO companyDTO) {
        Company company = companyService.findOne(companyDTO.getId());
        if (company == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        company.setName(companyDTO.getName());
        company.setLocation(companyDTO.getLocation());
        company.setGrade(companyDTO.getGrade());
        company.setStartTime(LocalTime.parse(companyDTO.getStartTime(), formatter));
        company.setEndTime(LocalTime.parse(companyDTO.getEndTime(), formatter));

        company = companyService.save(company);
        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", path = "/equipment-update")
    public ResponseEntity<CompanyDTO> updateCompanyEquipment(@RequestBody CompanyDTO companyDTO) {
        Company company = companyService.findOne(companyDTO.getId());
        if (company == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Set<Equipment> oldEquipmentSet = companyDTO.getEquipment();
        Set<Equipment> newEquipmentSet = new HashSet<>();
        List<CompanyEquipment> existingCompanyEquipmentList = companyEquipmentService.findAllByCompany(company);
        List<EquipmentAmountDTO> updatedEquipmentList = companyDTO.getEquipmentAmountInStock();

        updatedEquipmentList.forEach(updatedEquipmentInStock -> {
            Equipment updatedEquipment = equipmentService.findOne(updatedEquipmentInStock.getEquipment().getId());

            // Update equipment that is in the updated list
            if (oldEquipmentSet.contains(updatedEquipment)) {
                CompanyEquipment updatedCompanyEquipment = new CompanyEquipment(company, updatedEquipment, updatedEquipmentInStock.getQuantity());
                companyEquipmentService.save(updatedCompanyEquipment);
            }

            // Add new equipment entries
            if (!oldEquipmentSet.contains(updatedEquipment)) {
                CompanyEquipment newCompanyEquipment = new CompanyEquipment(company, updatedEquipment, updatedEquipmentInStock.getQuantity());
                companyEquipmentService.save(newCompanyEquipment);
            }

            newEquipmentSet.add(updatedEquipment);
        });

        // Remove equipment that is not in the updated set
        existingCompanyEquipmentList.forEach(companyEquipment -> {
            if (!newEquipmentSet.contains(companyEquipment.getEquipment())) {
                companyEquipmentService.remove(companyEquipment.getId());
            }
        });

        company.setEquipments(newEquipmentSet);
        //company.setEquipments(companyDTO.getEquipment());
        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }
    private boolean containsEquipment(List<CompanyEquipment> companyEquipmentList, Equipment equipment) {
        return companyEquipmentList.stream()
                .anyMatch(companyEquipment -> companyEquipment.getEquipment().equals(equipment));
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
