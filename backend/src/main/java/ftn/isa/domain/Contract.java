package ftn.isa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "companyId", referencedColumnName = "id")
    private Company company;
    @Column(name= "hospital")
    private String hospital;
    @Column(name= "hospitalAddress")
    private String hospitalAddress;
    @Column(name= "date", nullable = false)
    private LocalDateTime date;
    @Column(name = "valid", nullable = false)
    private boolean valid;
    @ManyToOne
    @JoinColumn(name="equipmentId", referencedColumnName = "id")
    private Equipment equipment;
    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Contract(Integer id, Company company, String hospital, String hospitalAddress, LocalDateTime date, boolean isValid, Equipment equipment, Integer amount) {
        this.id = id;
        this.company = company;
        this.hospital = hospital;
        this.hospitalAddress = hospitalAddress;
        this.date = date;
        this.valid = isValid;
        this.equipment = equipment;
        this.amount = amount;
    }

    public Contract() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
