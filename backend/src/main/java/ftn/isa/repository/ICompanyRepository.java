package ftn.isa.repository;

import ftn.isa.domain.Company;
import ftn.isa.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICompanyRepository extends JpaRepository<Company, Integer> {
    public Page<Company> findAll(Pageable pageable);
    public List<Company> findAll();
    //public Company findByName(String name);

    @Query("select s from Company s where s.Name = :name")
    public Company findByName(@Param("name") String name);

    @Query("select s from Company s where s.Id = :id")
    public Company find(@Param("id") int id);
    //public List<Equipment> findEquipment();
    //public List<Company> findAllByName(String name);
}

