package ftn.isa.service;

import ftn.isa.domain.CompanyEquipmentId;
import ftn.isa.repository.ICompanyEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ftn.isa.domain.CompanyEquipment;
import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import javax.transaction.Transactional;
import java.util.List;

@Service
public class CompanyEquipmentService {
    @Autowired
    private ICompanyEquipmentRepository companyEquipmentRepository;

    public Page<CompanyEquipment> findAll(Pageable pageable) { return companyEquipmentRepository.findAll(pageable); }

    public List<CompanyEquipment> findAll() { return companyEquipmentRepository.findAll(); }
    public List<CompanyEquipment> findAllByCompany(Company company) { return companyEquipmentRepository.findAllByCompany(company); }
    public List<CompanyEquipment> findAllByEquipment(Equipment equipment) { return companyEquipmentRepository.findAllByEquipment(equipment); }
    public CompanyEquipment findOne(CompanyEquipmentId id) { return companyEquipmentRepository.findOne(id); }
    @Transactional
    public CompanyEquipment save(CompanyEquipment companyEquipment) { return companyEquipmentRepository.save(companyEquipment); }
    @Transactional
    public void remove(CompanyEquipmentId id){
        companyEquipmentRepository.deleteByCompanyEquipmentId(id.getCompanyId(), id.getEquipmentId());
    }
}
