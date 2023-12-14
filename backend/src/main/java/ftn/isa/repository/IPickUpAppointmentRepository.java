package ftn.isa.repository;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;
import ftn.isa.domain.PickUpAppointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
public interface IPickUpAppointmentRepository extends JpaRepository<PickUpAppointment, Integer> {
    public Page<PickUpAppointment> findAll(Pageable pageable);
    public List<PickUpAppointment> findAllByCompanyAdmin(CompanyAdmin companyAdmin);

    @Query("SELECT p FROM PickUpAppointment p " +
            "WHERE p.companyAdmin = :companyAdmin " +
            "AND p.date >= :startDate " +
            "AND p.date <= :endDate")
    public List<PickUpAppointment> findAllByCompanyAdminOnSameDay(CompanyAdmin companyAdmin, LocalDateTime startDate, LocalDateTime endDate);
}
