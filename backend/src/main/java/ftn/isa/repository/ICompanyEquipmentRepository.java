package ftn.isa.repository;

import ftn.isa.domain.CompanyEquipment;
import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICompanyEquipmentRepository extends JpaRepository<CompanyEquipment, Integer> {
    public Page<CompanyEquipment> findAll(Pageable pageable);
    public List<CompanyEquipment> findAll();
    public List<CompanyEquipment> findAllByCompany(Company company);
    public List<CompanyEquipment> findAllByEquipment(Equipment equipment);
}
