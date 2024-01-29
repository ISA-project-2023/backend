package ftn.isa.domain;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "\"equipment\"")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name", unique = true, nullable = false)
    private String name;
    @Column(name="type", nullable = false)
    private String type;
    @Column(name="description", nullable = true)
    private String description;

    public Equipment(){ super(); }
    public Equipment(Integer id, String name, String type, String description, Integer quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Equipment e = (Equipment) o;
        if (e.name == null || name == null){
            return false;
        }
        return Objects.equals(name, e.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }

    @Override
    public String toString() {
        return "Equipment [id= " + id + ", name= " + name + ", description= " + description + "]";
    }
}