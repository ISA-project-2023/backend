package ftn.isa.repository;

import ftn.isa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByToken(String token);

    @Query("SELECT s FROM Customer s WHERE s.id = :id")
    Customer find(@Param("id") Integer id);
}
