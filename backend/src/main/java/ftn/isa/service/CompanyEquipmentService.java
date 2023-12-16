package ftn.isa.service;

import ftn.isa.repository.ICompanyEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ftn.isa.domain.CompanyEquipment;
import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class CompanyEquipmentService {
    @Autowired
    private ICompanyEquipmentRepository companyEquipmentRepository;

    public CompanyEquipment findOne(Integer id) { return companyEquipmentRepository.findById(id).orElseGet(null); }

    public Page<CompanyEquipment> findAll(Pageable pageable) { return companyEquipmentRepository.findAll(pageable); }

    public List<CompanyEquipment> findAll() { return companyEquipmentRepository.findAll(); }
    public List<CompanyEquipment> findAllByCompany(Company company) { return companyEquipmentRepository.findAllByCompany(company); }
    public List<CompanyEquipment> findAllByEquipment(Equipment equipment) { return companyEquipmentRepository.findAllByEquipment(equipment); }

    public CompanyEquipment save(CompanyEquipment companyEquipment) {
        // TODO check if that combination of company and equipment already exists
        return companyEquipmentRepository.save(companyEquipment);
    }

    public void remove(Integer id){ companyEquipmentRepository.deleteById(id); }
}
