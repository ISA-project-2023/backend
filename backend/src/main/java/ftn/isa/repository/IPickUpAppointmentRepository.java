package ftn.isa.repository;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.PickUpAppointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface IPickUpAppointmentRepository extends JpaRepository<PickUpAppointment, Integer> {
    public Page<PickUpAppointment> findAll(Pageable pageable);
    @Query("SELECT r FROM PickUpAppointment r WHERE r.companyAdmin.id = :id")
    public List<PickUpAppointment> findAllByCompanyAdminId(Integer id);
}
