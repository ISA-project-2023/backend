package ftn.isa.controller;

import ftn.isa.domain.Equipment;
import ftn.isa.dto.EquipmentDTO;
import ftn.isa.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<EquipmentDTO>> getAll() {
        List<Equipment> equipment = equipmentService.findAll();
        List<EquipmentDTO> equipmentDTOs = new ArrayList<>();
        for (Equipment e : equipment){
            equipmentDTOs.add(new EquipmentDTO(e));
        }
        return new ResponseEntity<>(equipmentDTOs, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getEquipmentPage(Pageable page) {
        List<Equipment> equipment = equipmentService.findAll(page).toList();
        List<EquipmentDTO> equipmentDTOs = new ArrayList<>();
        for (Equipment e : equipment){
            equipmentDTOs.add(new EquipmentDTO(e));
        }
        return new ResponseEntity<>(equipmentDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipmentDTO> getEquipment(@PathVariable Integer id) {
        Equipment e = equipmentService.findOne(id);
        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new EquipmentDTO(e), HttpStatus.OK);
    }


    /*
    @PostMapping(consumes = "application/json")
    public ResponseEntity<EquipmentDTO> saveEquipment(@RequestBody Map<String, Object> requestBody) {
    Map<String, Object> EquipmentDTOMap = (Map<String, Object>) requestBody.get("equipmentDTO");
    EquipmentDTO equipmentDTO = new EquipmentDTO((Integer) EquipmentDTOMap.get("id"),(String) EquipmentDTOMap.get("name"),(String) EquipmentDTOMap.get("description"));
    String name = (String) requestBody.get("name");
    Equipment equipment = new Equipment();
    equipment.setName(equipmentDTO.getName());
    equipment.setDescription(equipmentDTO.getDescription());

    equipment = equipmentService.save(equipment);
    return new ResponseEntity<>(new EquipmentDTO(equipment), HttpStatus.CREATED);
    } */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<EquipmentDTO> saveEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = new Equipment();
        equipment.setName(equipmentDTO.getName());
        equipment.setDescription(equipmentDTO.getDescription());
        equipment.setType(equipmentDTO.getType());

        equipment = equipmentService.save(equipment);
        return new ResponseEntity<>(new EquipmentDTO(equipment), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<EquipmentDTO> updateEquipment(@RequestBody EquipmentDTO equipmentDTO) {

        Equipment equipment = equipmentService.findOne(equipmentDTO.getId());
        if (equipment == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        equipment.setName(equipmentDTO.getName());
        equipment.setType(equipmentDTO.getType());
        equipment.setDescription(equipmentDTO.getDescription());

        equipment = equipmentService.save(equipment);
        return new ResponseEntity<>(new EquipmentDTO(equipment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Integer id) {
        Equipment equipment = equipmentService.findOne(id);

        if (equipment != null) {
            equipmentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findName")
    public ResponseEntity<EquipmentDTO> getEquipmentByName(@RequestParam String name) {
        Equipment equipment = equipmentService.findOneByName(name);

        if (equipment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new EquipmentDTO(equipment), HttpStatus.OK);
    }
}