package ftn.isa.controller;

import ftn.isa.domain.Company;
import ftn.isa.domain.Contract;
import ftn.isa.domain.Equipment;
import ftn.isa.dto.ContractDTO;
import ftn.isa.service.CompanyService;
import ftn.isa.service.ContractService;
import ftn.isa.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
    private EquipmentService equipmentService;

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ContractDTO> addContract(@RequestBody ContractDTO contractDTO){
        Company company = companyService.findOneByName(contractDTO.getCompany());
        //Equipment equipment = equipmentService.findOneByName(contractDTO.getEquipment());
        Equipment equipment = null;
        for(Equipment e: company.getEquipments()){
            if(e.getName().equals(contractDTO.getEquipment())){
                equipment = e;
                break;
            }
        }
        Contract contract = new Contract(null, company, contractDTO.getHospital(), contractDTO.getHospitalAddress(), contractDTO.getDate(), true, equipment, contractDTO.getAmount());
        Contract savedContract = contractService.save(contract);
        return new ResponseEntity<>(new ContractDTO(savedContract), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", path = "/cancel")
    public ResponseEntity<ContractDTO> cancelContract(@RequestBody ContractDTO contractDTO){
        Contract contract = contractService.cancel(contractDTO.getId());
        //If successful, send message to hospital about cancellation.
        return new ResponseEntity<>(new ContractDTO(contract), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", path = "/deliver")
    public ResponseEntity<ContractDTO> deliver(@RequestBody ContractDTO contractDTO){
        Contract contract = contractService.deliver(contractDTO.getId());
        //If successful, send message to hospital about delivery.
        return new ResponseEntity<>(new ContractDTO(contract), HttpStatus.OK);
    }
//    CompanyService companyService = new CompanyService();
//    ContractService service = new ContractService();
//    List<Contract> contracts = new ArrayList<>();
//        for(var company: companyService.findAll()){
//        List<Contract> contractsForCompany = service.findValidByCompany(company);
//        if(contractsForCompany.get(0)!=null)
//            contracts.add(contractsForCompany.get(0));
//    }
//    List<ScheduledExecutorService> schedulers = new ArrayList<>();
//        for(int i = 0; i<contracts.size(); i++){
//        schedulers.set(i, Executors.newScheduledThreadPool(1));
//        int finalI = i;
//        schedulers.get(i).scheduleAtFixedRate(() -> deliver(contracts.get(finalI).getCompany().getName()), Duration.between(LocalDateTime.now(), contracts.get(finalI).getDate()).toDays(), 30, TimeUnit.SECONDS);
//    }
}
