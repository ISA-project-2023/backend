package ftn.isa.domain;

import javax.persistence.*;

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
    private Employee employee;
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private ReservationStatus status;

    public Reservation(Integer id, PickUpAppointment pickUpAppointment, Employee employee, ReservationStatus status) {
        this.id = id;
        this.pickUpAppointment = pickUpAppointment;
        this.employee = employee;
        this.status = status;
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

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
