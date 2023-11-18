package ftn.isa.service;

import ftn.isa.domain.Company;
import ftn.isa.domain.CompanyAdmin;
import ftn.isa.repository.ICompanyAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyAdminService {
    @Autowired
    private ICompanyAdminRepository companyAdminRepository;
    public CompanyAdmin findOne(Integer id) { return companyAdminRepository.findById(id).orElseGet(null); }
    public List<CompanyAdmin> findAll() {
        return companyAdminRepository.findAll();
    }
    public Page<CompanyAdmin> findAll(Pageable page) {
        return companyAdminRepository.findAll(page);
    }
    public CompanyAdmin save(CompanyAdmin user) {
        return companyAdminRepository.save(user);
    }
    public void remove(Integer id) {
        companyAdminRepository.deleteById(id);
    }
    public List<CompanyAdmin> findAllByCompany(Company company) { return companyAdminRepository.findAllByCompany(company); }
}
