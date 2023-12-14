package ftn.isa.repository;

import ftn.isa.domain.Customer;
import ftn.isa.domain.SystemAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ISystemAdminRepository extends JpaRepository<SystemAdmin, Integer> {

    @Query("SELECT s FROM SystemAdmin s WHERE s.id = :id")
    SystemAdmin find(@Param("id") Integer id);
}
