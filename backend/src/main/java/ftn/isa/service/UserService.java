package ftn.isa.service;

import ftn.isa.domain.User;
import ftn.isa.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    public User findOne(Integer id) {
        return userRepository.findById(id).orElseGet(null);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }
    public User save(User user) {
        return userRepository.save(user);
    }
    public void remove(Integer id) {
        userRepository.deleteById(id);
    }
    public User findByUsername(String index) { return userRepository.findOneByUsername(index); }
    public List<User> findByLastName(String lastName) { return userRepository.findAllByLastName(lastName); }
    public List<User> findByFirstNameAndLastName(String firstName, String lastName) { return userRepository.findByFirstNameAndLastNameAllIgnoringCase(firstName, lastName); }
    public List<User> findPremiumUsers() {
        return userRepository.findPremiumUsers();
    }
    private boolean passwordMatches(String password, String password1) {
        return password.matches(password1);
    }
    public User authenticate(String username, String password) {
        User user = findByUsername(username);

        if (user != null && passwordMatches(password, user.getPassword())) {
            return user;
        }

        return null;
    }
}
