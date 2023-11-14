package ftn.isa.repository;

import ftn.isa.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface IEquipmentRepository extends JpaRepository<Equipment, Integer> {
    public Equipment findOneByName(String name);
    public Page<Equipment> findAll(Pageable pageable);
}
