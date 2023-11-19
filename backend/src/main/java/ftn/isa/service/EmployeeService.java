package ftn.isa.service;

import ftn.isa.domain.Employee;
import ftn.isa.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private IEmployeeRepository employeeRepository;
    public Employee save(Employee employee) {
        try {
            Employee savedEmployee = employeeRepository.save(employee);
            return savedEmployee;
        } catch (Exception e) {
            throw e;
        }
    }

    public Employee findByToken(String token) {
        return employeeRepository.findByToken(token);
    }
    public Employee find(Integer id){return employeeRepository.find(id);}
}
