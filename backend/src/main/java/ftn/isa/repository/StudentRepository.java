package ftn.isa.repository;
import ftn.isa.domain.Student;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    public Student findOneByIndex(String index);

    public Page<Student> findAll(Pageable pageable);

    public List<Student> findAllByLastName(String lastName);

    public List<Student> findByFirstNameAndLastNameAllIgnoringCase(String firstName, String lastName);

    @Query("select s from Student s where s.lastName = ?1")
    public List<Student> pronadjiStudentePoPrezimenu(String prezime);

}
