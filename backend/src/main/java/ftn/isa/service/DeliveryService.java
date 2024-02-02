package ftn.isa.service;

import ftn.isa.domain.Contract;
import ftn.isa.domain.ContractProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private ContractService service;

    @Autowired
    private ContractProducer producer;
    @Scheduled(cron = "0 0 * * * *")
    private void setDeliverySchedule(){
            LocalDateTime now = LocalDateTime.now();
            List<Contract> contracts = service.findAllValid(true);
            for (Contract contract : contracts) {
                if(contract.getDate().getHour() == now.getHour()){
                    String message = contract.getCompany().getName() + " started delivery";
                    producer.sendTo("spring-boot4", message);
                    contract = service.deliver(contract.getId());
                }
            }
    }
}
