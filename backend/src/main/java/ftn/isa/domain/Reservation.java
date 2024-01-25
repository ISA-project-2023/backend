package ftn.isa.domain;

import javax.persistence.*;
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
    private List<Equipment> equipment;

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
    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
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
//        StringBuilder eq = new StringBuilder();
//        if (equipment != null) {
//            for (Equipment e : equipment) {
//                eq.append(e.getName()).append(" (").append(e.getDescription()).append("), ");
//            }
//        }
        return "Reservation Details: \n" +
                "Company: " + (company != null ? company.getName() : "") +
                "\nCustomer: " + (customer != null ? customer.getFirstName() + " " + customer.getLastName() : "") +
//                "\nEquipment: " + eq.toString() +
                "\nPickup date: " + (pickUpAppointment != null ? pickUpAppointment.getDate() : "");
    }

}
