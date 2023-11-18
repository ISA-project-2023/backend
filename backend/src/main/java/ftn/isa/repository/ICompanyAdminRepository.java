package ftn.isa.repository;

import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICompanyAdminRepository extends JpaRepository<CompanyAdmin, Integer> {
    public Page<CompanyAdmin> findAll(Pageable pageable);
    public List<CompanyAdmin> findAllByCompany(Company company);
}