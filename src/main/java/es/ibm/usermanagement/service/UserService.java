package es.ibm.usermanagement.service;


import es.ibm.usermanagement.dto.UserRequest;
import es.ibm.usermanagement.entity.User;
import es.ibm.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequest userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setSubscribed(userDTO.isSubscribed());
        user.setPostalCode(userDTO.getPostalCode());
        return userRepository.save(user);
    }

    public Optional<User> getUserByUuid(String uuid) {
        return userRepository.findById(uuid);
    }

    public Page<User> searchUsers(String firstName, Integer age, Pageable pageable) {
        return userRepository.searchUsers(firstName, age, pageable);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();

    }
}
