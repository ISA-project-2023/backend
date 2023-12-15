package ftn.isa.repository;

import ftn.isa.domain.ComplaintAboutCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IComplaintAboutCompanyRepository extends JpaRepository<ComplaintAboutCompany, Integer> {

    public List<ComplaintAboutCompany> findAll();
}
