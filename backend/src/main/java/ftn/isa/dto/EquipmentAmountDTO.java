package ftn.isa.dto;

import ftn.isa.domain.Equipment;

public class EquipmentAmountDTO {
    private Equipment equipment;
    private Integer quantity;

    public Equipment getEquipment() { return equipment; }
    public Integer getQuantity() { return quantity; }

    public EquipmentAmountDTO() {}
    public EquipmentAmountDTO(Equipment equipment, Integer quantity) {
        this.equipment = equipment;
        this.quantity = quantity;
    }
}
