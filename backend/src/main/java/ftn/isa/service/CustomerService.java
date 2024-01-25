package ftn.isa.service;

import ftn.isa.domain.Customer;
import ftn.isa.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService {
    @Autowired
    private ICustomerRepository customerRepository;
    public Customer save(Customer customer) {
        try {
            Customer savedCustomer = customerRepository.save(customer);
            return savedCustomer;
        } catch (Exception e) {
            throw e;
        }
    }


    public Customer findByToken(String token) {
        return customerRepository.findByToken(token);
    }
    public Customer find(Integer id){return customerRepository.find(id);}
}
