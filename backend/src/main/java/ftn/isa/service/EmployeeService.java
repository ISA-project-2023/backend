package ftn.isa.service;

import ftn.isa.domain.Employee;
import ftn.isa.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private IEmployeeRepository employeeRepository;

    public Employee findByUsername(String username){
        return employeeRepository.findOneByUsername(username);
    }
}
