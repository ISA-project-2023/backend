package ftn.isa.service;

import ftn.isa.domain.ComplaintAboutAdmin;
import ftn.isa.domain.ComplaintAboutCompany;
import ftn.isa.repository.IComplaintAboutAdminRepository;
import ftn.isa.repository.IComplaintAboutCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ComplaintService {
    @Autowired
    private IComplaintAboutCompanyRepository complaintAboutCompanyRepository;
    @Autowired
    private IComplaintAboutAdminRepository complaintAboutAdminRepository;

    public List<ComplaintAboutCompany> findAllComplaintsAboutCompany(){ return complaintAboutCompanyRepository.findAll(); }

    public List<ComplaintAboutAdmin> findAllComplaintsAboutAdmin(){return complaintAboutAdminRepository.findAll();}
}
