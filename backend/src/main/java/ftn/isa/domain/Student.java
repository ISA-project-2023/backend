package ftn.isa.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "indexNumber", unique = true, nullable = false)
    private String index;

    /*
     * Anotacija @Column oznacava da ce neki atribut biti kolona u tabeli
     */
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    public Student() {
        super();
    }

    public Student(Integer id, String index, String firstName, String lastName) {
        super();
        this.id = id;
        this.index = index;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /*
     * Pri implementaciji equals and hashCode metoda treba obratiti paznju da se
     * one razlikuju kada se koristi ORM (Hibernate) i kada se klase posmatraju kao
     * obicne POJO klase. Hibernate zahteva da entitet mora biti jednak samom sebi kroz sva
     * stanja tog objekta (tranzijentni (novi objekat), perzistentan (persistent), otkacen (detached) i obrisan (removed)).
     * To znaci da bi dobar pristup bio da se za generisanje equals i hashCode metoda koristi podatak
     * koji je jedinstven a poznat unapred (tzv. business key) npr. index studenta, isbn knjige, itd.
     * U slucaju da takvog obelezja nema, obicno se implementacija svodi na proveri da li je id (koji je kljuc) isti.
     * Posto u velikom broju slucajeva id baza generise, to znaci da u tranzijentnom stanju objekti nisu jednaki.
     * Postoji nekoliko resenja za ovaj problem:
     * 1. Naci neko jedinstveno obelezje
     * 2. Koristiti prirodne kljuceve
     * 3. Pre cuvanja na neki nacin saznati koja je sledeca vrednost koju ce baza generisati pa pozvati setId metodu da se kompletira objekat cak i pre cuvanja
     * 4. Na drugi nacin implementirati equals i hashCode - primer u klasi Teacher
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student s = (Student) o;
        if (s.index == null || index == null) {
            return false;
        }
        return Objects.equals(index, s.index);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", index=" + index + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}
