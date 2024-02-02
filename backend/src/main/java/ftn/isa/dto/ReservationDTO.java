package ftn.isa.dto;

import ftn.isa.domain.*;
import java.util.List;

public class ReservationDTO {
    private Integer id;
    private PickUpAppointment pickUpAppointment;
    private Customer customer;
    private ReservationStatus status;
    private Company company;
    private List<EquipmentAmountDTO> equipment;

    public ReservationDTO(){}
    public ReservationDTO(Integer id, PickUpAppointment pickUpAppointment, Customer customer, ReservationStatus status, Company company, List<EquipmentAmountDTO> equipment) {
        this.id = id;
        this.pickUpAppointment = pickUpAppointment;
        this.customer = customer;
        this.status = status;
        this.company = company;
        this.equipment = equipment;
    }
    public ReservationDTO(Reservation reservation){
        this(reservation.getId(), reservation.getPickUpAppointment(), reservation.getCustomer(), reservation.getStatus(), reservation.getCompany(), reservation.getEquipment());
    }

    public Integer getId() {
        return id;
    }

    public PickUpAppointment getPickUpAppointment() {
        return pickUpAppointment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Company getCompany() {
        return company;
    }

    public List<EquipmentAmountDTO> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<EquipmentAmountDTO> equipment) {
        this.equipment = equipment;
    }
}
