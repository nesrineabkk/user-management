package es.ibm.usermanagement.controller;


import es.ibm.usermanagement.dto.UserRequest;
import es.ibm.usermanagement.entity.User;
import es.ibm.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUsers(@RequestParam(required = false) String firstName,
                                                  @RequestParam(required = false) Integer age,
                                                  Pageable pageable) {
        return ResponseEntity.ok(userService.searchUsers(firstName, age, pageable));

    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

}
