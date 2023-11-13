package ftn.isa.service;
import ftn.isa.domain.Company;
import ftn.isa.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private ICompanyRepository companyRepository;

    public List<Company> findAll(){ return companyRepository.findAll();}
    public Page<Company> findAll(Pageable page){ return companyRepository.findAll(page);}
}
