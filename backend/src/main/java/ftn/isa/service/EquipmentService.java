package ftn.isa.service;

import ftn.isa.domain.Equipment;
import ftn.isa.repository.IEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private IEquipmentRepository equipmentRepository;

    public Equipment findOne(Integer id){
        return equipmentRepository.findById(id).orElseGet(null);
    }
    public Equipment findOneByName(String name){
        return equipmentRepository.findOneByName(name);
    }
    public Page<Equipment> findAll(Pageable pageable){
        return equipmentRepository.findAll(pageable);
    }
    public List<Equipment> findAll(){
        return equipmentRepository.findAll();
    }
    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }
    public void remove(Integer id) {
        equipmentRepository.deleteById(id);
    }
}