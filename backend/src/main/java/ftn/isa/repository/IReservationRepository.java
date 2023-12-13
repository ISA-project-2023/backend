package ftn.isa.repository;

import ftn.isa.domain.Company;
import ftn.isa.domain.Customer;
import ftn.isa.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.AssertTrue;
import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId")
    List<Reservation> findAllByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT r FROM Reservation r WHERE r.company.Id = :companyId")
    List<Reservation> findAllByCompanyId(@Param("companyId") Integer companyId);
}
