package ftn.isa.controller;

import ftn.isa.domain.Company;
import ftn.isa.domain.Contract;
import ftn.isa.domain.ContractProducer;
import ftn.isa.dto.ContractDTO;
import ftn.isa.service.CompanyService;
import ftn.isa.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/contracts")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ContractProducer producer;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        List<Contract> contracts = contractService.findAll();
        List<ContractDTO> contractsDTO = new ArrayList<>();
        for (Contract s : contracts) {
            contractsDTO.add(new ContractDTO(s));
        }
        return new ResponseEntity<>(contractsDTO, HttpStatus.OK);
    }
    @GetMapping(value = "/{companyName}")
    public ResponseEntity<List<ContractDTO>> getValidContractByCompany(@PathVariable String companyName) {
        Company company = companyService.findOneByName(companyName);
        List<Contract> contracts = contractService.findValidByCompany(company);
        List<ContractDTO> contractDTOs = new ArrayList<>();
        for(Contract c: contracts){
            contractDTOs.add(new ContractDTO(c));
        }
        return new ResponseEntity<>(contractDTOs, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", path = "/cancel")
    public ResponseEntity<ContractDTO> cancelContract(@RequestBody ContractDTO contractDTO){
        Contract contract = contractService.cancel(contractDTO.getId());
        if(contract != null){
            String message = contract.getCompany().getName() + " canceled delivery for this month.";
            producer.sendTo("spring-boot4", message);
        }
        return new ResponseEntity<>(new ContractDTO(contract), HttpStatus.OK);
    }


}
