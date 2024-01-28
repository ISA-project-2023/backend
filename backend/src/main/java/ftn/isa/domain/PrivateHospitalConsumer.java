package ftn.isa.domain;

import ftn.isa.dto.ContractDTO;
import ftn.isa.service.CompanyService;
import ftn.isa.service.ContractService;
import ftn.isa.controller.PrivateHospitalController;
import ftn.isa.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PrivateHospitalConsumer {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ContractService contractService;

    private static final Logger log = LoggerFactory.getLogger(PrivateHospitalConsumer.class);

    /* @RabbitListener anotira metode za kreiranje handlera za bilo koju poruku koja pristize,
     * sto znaci da ce se kreirati listener koji je konektovan na RabbitQM queue i koji ce
     * prosledjivati poruke metodi. Listener ce konvertovati poruku u odgovorajuci tip koristeci
     * odgovarajuci konvertor poruka (implementacija org.springframework.amqp.support.converter.MessageConverter interfejsa).   */
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "${myqueue3}", durable = "true"),
                    exchange = @Exchange(value = "${exchange2}")))
    public void handler(String message){
        log.info("Consumer> " + message);
        ContractDTO contractDTO = deserializeMessageToContractDTO(message);
        if (contractDTO != null){
            createNewContract(contractDTO);
            log.info("\nContract added successfully!\n");
        }
    }

    private ContractDTO deserializeMessageToContractDTO(String message){
        String[] parts = message.split(",");
        // check if there are right number of string in parts
        if (parts.length != 6){
            return null;
        }

        // message: Hospital1,Address1,Lekovi za ujed komarca,5,Medicine Solution,2024-02-15T12:00:00
        ContractDTO contractDTO = new ContractDTO(null, parts[0], parts[1], parts[2], parts[4], LocalDateTime.parse(parts[5]), true, Integer.parseInt(parts[3]));
        return contractDTO;
    }

    private void createNewContract(ContractDTO contractDTO){
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
    }
}
