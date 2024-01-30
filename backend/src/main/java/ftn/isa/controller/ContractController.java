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
        //If successful, send message to hospital about cancellation.
        if(contract != null){
            String message = contract.getCompany().getName() + " canceled delivery for this month.";
            //PrivateHospitalController.sendCancellationMessage(message);
            producer.sendTo("spring-boot4", message);
        }
        return new ResponseEntity<>(new ContractDTO(contract), HttpStatus.OK);
    }

//    @PutMapping(consumes = "application/json", path = "/deliver")
//    public ResponseEntity<ContractDTO> deliver(@RequestBody ContractDTO contractDTO){
//        Contract contract = contractService.deliver(contractDTO.getId());
//        //If successful, send message to hospital about delivery.
//        if(contract != null){
//            String message = contract.getCompany().getName() + " started delivery.";
//            PrivateHospitalController.sendDeliveryMessage(message);
//            producer.sendTo("spring-boot4", message);
//        }
//        return new ResponseEntity<>(new ContractDTO(contract), HttpStatus.OK);
//    }

    // TODO - fix this
//    private void scheduleContractsDelivery(){
//        //ContractService service = new ContractService();
//        //List<Contract> validContracts = service.findAllValid(true);
//        List<Contract> validContracts = contractService.findAllValid(true);
//
//
//        // set schedule for every contract
//        //List<ScheduledExecutorService> schedulers = new ArrayList<>();
//        for(int i = 0; i<validContracts.size(); i++){
//
////            schedulers.set(i, Executors.newScheduledThreadPool(1));
////            int finalI = i;
////            //schedulers.get(i).scheduleAtFixedRate(() -> deliver(validContracts.get(finalI).getCompany().getName()), Duration.between(LocalDateTime.now(), validContracts.get(finalI).getDate()).toDays(), 30, TimeUnit.MINUTES);
//
////            delay = Duration.between(LocalDateTime.now(), validContracts.get(i).getDate()).toSeconds();
////            schedulers.get(i).schedule(() -> startDelivery(validContracts.get(i), delay, TimeUnit.SECONDS););
//        }
//    }
//
//    private void startDelivery(Contract contract) {
//        if(contract != null){
//            String message = contract.getCompany().getName() + " started delivery!";
//            Contract contractDeliverStarted = contractService.deliver(contract.getId());
//            PrivateHospitalController.sendDeliveryMessage(message);
//            producer.sendTo("spring-boot4", message);
//        }
//    }
//
////    // Add this method to your class
////    private void shutdownExecutors(List<ScheduledExecutorService> schedulers) {
////        for (ScheduledExecutorService scheduler : schedulers) {
////            scheduler.shutdown();
////        }
////    }
////
////    // Call this method when your application is shutting down
////// For example, you can add a @PreDestroy method in a @Component or @Service class
////    @PreDestroy
////    public void destroy() {
////        shutdownExecutors(schedulers);
////    }
}
