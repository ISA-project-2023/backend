package ftn.isa.repository;

import ftn.isa.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    public Employee findOneByUsername(String username);

}
