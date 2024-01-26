package ftn.isa.repository;

import ftn.isa.domain.Company;
import ftn.isa.domain.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContractRepository extends JpaRepository<Contract, Integer> {
    public Page<Contract> findAll(Pageable pageable);
    public List<Contract> findAll();
    public List<Contract> findAllByCompany(Company company);
    public Contract findByCompanyAndValid(Company company, boolean valid);
}
