package ftn.isa.service;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyEquipment;
import ftn.isa.domain.Equipment;
import ftn.isa.repository.ICompanyEquipmentRepository;
import ftn.isa.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CompanyService {
    @Autowired
    private ICompanyRepository companyRepository;
    @Autowired
    private ICompanyEquipmentRepository companyEquipmentRepository;


    @Transactional
    public Company findOneByName(String name){
        Company comp = companyRepository.findByName(name);
        List<CompanyEquipment> companyEquipmentList = companyEquipmentRepository.findAllByCompany(comp);
        Set<Equipment> equipmentSet = new HashSet<>();
        for (CompanyEquipment companyEquipment:companyEquipmentList){
            equipmentSet.add(companyEquipment.getEquipment());
        }
        comp.setEquipments(equipmentSet);
        return comp;
    }
    public List<Company> findAll(){ return companyRepository.findAll();}
    public Page<Company> findAll(Pageable page){ return companyRepository.findAll(page);}
    //public List<Equipment> findEquipment() { return companyRepository.findEquipment(); }
    //public List<Company> findByName(String name) { return companyRepository.findAllByName(name); }

    public Company findOne(Integer id) { return companyRepository.findById(id).orElseGet(null); }
    public Company save(Company company) { return companyRepository.save(company); }
    public void remove(Integer id) { companyRepository.deleteById(id); }
}
