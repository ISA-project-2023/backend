package ftn.isa.service;

import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;
import ftn.isa.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private ICompanyRepository companyRepository;

    public Company findOneByName(String name){
        return companyRepository.findByName(name);
    }
    public List<Company> findAll(){ return companyRepository.findAll();}
    public Page<Company> findAll(Pageable page){ return companyRepository.findAll(page);}
    //public List<Equipment> findEquipment() { return companyRepository.findEquipment(); }
    //public List<Company> findByName(String name) { return companyRepository.findAllByName(name); }

    public Company findOne(Integer id) { return companyRepository.findById(id).orElseGet(null); }
    public Company save(Company company) { return companyRepository.save(company); }
    public void remove(Integer id) { companyRepository.deleteById(id); }
}
