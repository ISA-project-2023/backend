package ftn.isa.repository;

import ftn.isa.domain.ComplaintAboutAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IComplaintAboutAdminRepository extends JpaRepository<ComplaintAboutAdmin, Integer> {

    public List<ComplaintAboutAdmin> findAll();
}
