package ftn.isa.repository;

import ftn.isa.domain.Company;
import ftn.isa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICompanyRepository extends JpaRepository<Company, Integer> {
    public Page<Company> findAll(Pageable pageable);
    public List<Company> findAll();
}

