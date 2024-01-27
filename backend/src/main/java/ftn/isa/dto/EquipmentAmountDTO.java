package ftn.isa.dto;

public class EquipmentAmountDTO {
    private EquipmentDTO equipment;
    private Integer quantity;

    public EquipmentDTO getEquipment() { return equipment; }
    public Integer getQuantity() { return quantity; }

    public EquipmentAmountDTO(EquipmentDTO equipment, Integer quantity) {
        this.equipment = equipment;
        this.quantity = quantity;
    }
}
