package ftn.isa.service;

import ftn.isa.domain.Customer;
import ftn.isa.domain.SystemAdmin;
import ftn.isa.domain.User;
import ftn.isa.repository.ISystemAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemAdminService {
    @Autowired
    public ISystemAdminRepository systemAdminRepository;

    public SystemAdmin save(SystemAdmin systemAdmin) {
        return systemAdminRepository.save(systemAdmin);
    }
    public SystemAdmin find(Integer id){return systemAdminRepository.find(id);}
}
