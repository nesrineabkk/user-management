package es.ibm.usermanagement.controller;


import es.ibm.usermanagement.dto.UserRequest;
import es.ibm.usermanagement.entity.User;
import es.ibm.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUsers(@RequestParam(required = false) String firstName,
                                                  @RequestParam(required = false) Integer age,
                                                  Pageable pageable) {
        Page<User> users = userService.searchUsers(firstName, age, pageable);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

}
