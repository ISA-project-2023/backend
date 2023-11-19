package ftn.isa.repository;

import ftn.isa.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByToken(String token);

    @Query("SELECT s FROM Employee s WHERE s.id = :id")
    Employee find(@Param("id") Integer id);
}
