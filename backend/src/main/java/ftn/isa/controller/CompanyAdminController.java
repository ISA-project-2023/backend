package ftn.isa.controller;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;
import ftn.isa.dto.CompanyAdminDTO;
import ftn.isa.service.CompanyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/users/companyAdmins")
public class CompanyAdminController {
    @Autowired
    private CompanyAdminService companyAdminService;

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

//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<CompanyAdminDTO> saveCompanyAdmin(@RequestBody Map<String, Object> requestBody) {
//
//        Map<String, Object> companyAdminDTOMap = (Map<String, Object>) requestBody.get("userDTO");
//        CompanyAdminDTO adminDTO = new CompanyAdminDTO((Integer) companyAdminDTOMap.get("id"),(String) companyAdminDTOMap.get("jobDescription") /*COMPANY*/);
//
//        CompanyAdmin admin = new CompanyAdmin();
//        admin.setJobDescription(adminDTO.getJobDescription());
//        admin.setCompany(adminDTO.getCompany());
//
//        admin = companyAdminService.save(admin);
//        return new ResponseEntity<>(new CompanyAdminDTO(admin), HttpStatus.CREATED);
//    }

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
}
