package ftn.isa.repository;

import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICompanyRepository extends JpaRepository<Company, Integer> {
    public Page<Company> findAll(Pageable pageable);
    public List<Company> findAll();
    //public List<Equipment> findEquipment();
    //public List<Company> findAllByName(String name);
}

