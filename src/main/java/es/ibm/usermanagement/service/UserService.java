package es.ibm.usermanagement.service;


import es.ibm.usermanagement.dto.UserRequest;
import es.ibm.usermanagement.entity.User;
import es.ibm.usermanagement.entity.UserAudit;
import es.ibm.usermanagement.repository.UserAuditRepository;
import es.ibm.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserAuditRepository userAuditRepository;



    @Autowired
    public UserService(UserRepository userRepository, UserAuditRepository userAuditRepository) {
        this.userRepository = userRepository;
        this.userAuditRepository = userAuditRepository;
    }

    public User createUser(UserRequest userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setSubscribed(userDTO.isSubscribed());
        user.setPostalCode(userDTO.getPostalCode());
        User savedUser = userRepository.save(user);
        UserAudit audit = new UserAudit(savedUser.getUuid(), UserAudit.AuditAction.CREATE, LocalDateTime.now());
        userAuditRepository.save(audit);
        return savedUser;
    }

    @Cacheable(value = "users", key = "{#firstName, #age, #pageable.pageNumber, #pageable.pageSize}")
    public Page<User> searchUsers(String firstName, Integer age, Pageable pageable) {
        return userRepository.searchUsers(firstName, age, pageable);
    }

    @Cacheable("allUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    private void logAudit(String userUuid, UserAudit.AuditAction action) {
        UserAudit audit = new UserAudit(userUuid, action, LocalDateTime.now());
        userAuditRepository.save(audit);
    }


}
