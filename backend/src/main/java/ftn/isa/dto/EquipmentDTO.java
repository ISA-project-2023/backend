package ftn.isa.dto;

import ftn.isa.domain.Equipment;
import javax.persistence.Column;
public class EquipmentDTO {
    private Integer id;
    private String name;
    private String description;

    public EquipmentDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public EquipmentDTO(Equipment equipment) {
        this(equipment.getId(), equipment.getName(), equipment.getDescription());
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
