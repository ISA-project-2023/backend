package ftn.isa.repository;

import ftn.isa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {

    public User findOneByUsername(String username);
    public Page<User> findAll(Pageable pageable);
    public List<User> findAllByLastName(String lastName);
    public List<User> findByFirstNameAndLastNameAllIgnoringCase(String firstName, String lastName);
    @Query("select s from User s where s.category = 'Premium'")
    public List<User> findPremiumUsers();

}
