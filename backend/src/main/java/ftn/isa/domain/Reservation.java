package ftn.isa.domain;

import ftn.isa.dto.EquipmentAmountDTO;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;

@Entity
@Table(name="\"reservation\"")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "pickUpAppointmentId", referencedColumnName = "id")
    private PickUpAppointment pickUpAppointment;
    @OneToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Customer customer;
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private ReservationStatus status;
    @ManyToOne
    @JoinColumn(name = "companyId", referencedColumnName = "id")
    private Company company;

    @Convert(converter = EquipmentListConverter.class)
    @Column(name = "equipment", columnDefinition = "TEXT")
    private List<EquipmentAmountDTO> equipment;

    public Reservation(Integer id, PickUpAppointment pickUpAppointment, Customer customer, ReservationStatus status, Company company) {
        this.id = id;
        this.pickUpAppointment = pickUpAppointment;
        this.customer = customer;
        this.status = status;
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    public List<EquipmentAmountDTO> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<EquipmentAmountDTO> equipment) {
        this.equipment = equipment;
    }

    public Reservation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PickUpAppointment getPickUpAppointment() {
        return pickUpAppointment;
    }

    public void setPickUpAppointment(PickUpAppointment pickUpAppointment) {
        this.pickUpAppointment = pickUpAppointment;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder reservationDetails = new StringBuilder();

        reservationDetails.append("Reservation Details: \n")
                .append("Company: ").append(company != null ? company.getName() : "").append("\n")
                .append("Customer: ").append(customer != null ? customer.getFirstName() + " " + customer.getLastName() : "").append("\n")
                .append("Company Admin: ").append(pickUpAppointment.getCompanyAdmin() != null ? pickUpAppointment.getCompanyAdmin().getFirstName() + " " + pickUpAppointment.getCompanyAdmin().getLastName() : "").append("\n")
                .append("Equipment: ");

        if (equipment != null) {
            for (Object equipmentItem : equipment) {
                if (equipmentItem instanceof EquipmentAmountDTO) {
                    EquipmentAmountDTO equipmentDto = (EquipmentAmountDTO) equipmentItem;
                    Equipment equipment = equipmentDto.getEquipment();
                    Integer quantity = equipmentDto.getQuantity();

                    if (equipment != null) {
                        reservationDetails.append(equipment.getName()).append(" (")
                                .append(equipment.getDescription()).append("), Quantity: ")
                                .append(quantity).append("\n");
                    }
                } else if (equipmentItem instanceof LinkedHashMap) {
                    // Handle the different structure (LinkedHashMap) accordingly
                    LinkedHashMap<?, ?> equipmentMap = (LinkedHashMap<?, ?>) equipmentItem;
                    LinkedHashMap<?, ?> innerEquipmentMap = (LinkedHashMap<?, ?>) equipmentMap.get("equipment");

                    Object name = innerEquipmentMap.get("name");
                    Object description = innerEquipmentMap.get("description");
                    Object quantity = equipmentMap.get("quantity");  // Assuming "quantity" is present in the outer map

                    if (name != null && description != null && quantity != null) {
                        reservationDetails.append(name).append(" (")
                                .append(description).append("), Quantity: ")
                                .append(quantity).append("\n");
                    }
                } else {
                    // If neither EquipmentAmountDTO nor LinkedHashMap nor expected structure, handle it accordingly
                    reservationDetails.append("Unexpected structure for equipment item\n");
                }
            }
        } else {
            reservationDetails.append("No equipment specified\n");
        }

        reservationDetails.append("Pickup date: ").append(pickUpAppointment != null ? pickUpAppointment.getDate() : "");

        return reservationDetails.toString();
    }




}
