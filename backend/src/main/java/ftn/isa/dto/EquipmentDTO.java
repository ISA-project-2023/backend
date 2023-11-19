package ftn.isa.dto;

import ftn.isa.domain.Equipment;
import javax.persistence.Column;
public class EquipmentDTO {
    private Integer id;
    private String name;
    private String type;
    private String description;

    public EquipmentDTO(Integer id, String name,String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public EquipmentDTO(Equipment equipment) {
        this(equipment.getId(), equipment.getName(), equipment.getType(), equipment.getDescription());
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() { return type; }
    public String getDescription() {
        return description;
    }
}
