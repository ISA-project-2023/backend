package ftn.isa.repository;

import ftn.isa.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ICompanyEquipmentRepository extends JpaRepository<CompanyEquipment, Integer> {
    public Page<CompanyEquipment> findAll(Pageable pageable);
    public List<CompanyEquipment> findAll();
    public List<CompanyEquipment> findAllByCompany(Company company);
    public List<CompanyEquipment> findAllByEquipment(Equipment equipment);

    @Query("SELECT p FROM CompanyEquipment p WHERE p.id =: id")
    public CompanyEquipment findOne(CompanyEquipmentId id);

    @Modifying
    @Query(value = "DELETE FROM company_equipment WHERE company_id = :companyId AND equipment_id = :equipmentId", nativeQuery = true)
    void deleteByCompanyEquipmentId(@Param("companyId") Integer companyId, @Param("equipmentId") Integer equipmentId);
}
