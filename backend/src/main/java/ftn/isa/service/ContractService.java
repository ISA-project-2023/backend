package ftn.isa.service;

import ftn.isa.domain.Company;
import ftn.isa.domain.Contract;
import ftn.isa.repository.IContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractService {
    @Autowired
    private IContractRepository contractRepository;
    public Contract save(Contract contract) {
        try {
            if(contract.getCompany()==null
                    || contract.getDate().isBefore(LocalDateTime.now())
                    || contract.getEquipment()==null
                    || contract.getAmount()<1
                    || contract.getHospital().isEmpty()){
                return null;
            }
            List<Contract> companyContracts = contractRepository.findAllByCompany(contract.getCompany());
            for(Contract c: companyContracts){
                if(c.isValid()){
                    c.setValid(false);
                    contractRepository.save(c);
                }
            }
            contract.setValid(true);
            Contract savedContract = contractRepository.save(contract);
            return savedContract;
        } catch (Exception e) {
            throw e;
        }
    }
    public List<Contract> findAll(){
            return contractRepository.findAll();
    }
    public List<Contract> findValidByCompany(Company company){
        return contractRepository.findAllByCompanyAndValid(company, true);
    }
    public List<Contract> findAllValid(boolean valid) { return contractRepository.findAllByValid(valid); }
    public Contract cancel(Integer id){
        Contract con = contractRepository.findContractById(id);
        if(!con.isValid()
                || con.getDate().isBefore(LocalDateTime.now().plusDays(3))
                || con.getDate().isAfter(LocalDateTime.now().plusMonths(1))){
            return null;
        }
        con.setDate(con.getDate().plusMonths(1));
        return contractRepository.save(con);
    }
    public Contract deliver(Integer id){
        Contract con = contractRepository.findContractById(id);
        if(!con.isValid() ||
                (con.getDate().getYear()!=LocalDateTime.now().getYear() || con.getDate().getDayOfYear()!=LocalDateTime.now().getDayOfYear())
        ){
            return null;
        }
        con.setDate(con.getDate().plusMonths(1));
        return contractRepository.save(con);
    }
}
