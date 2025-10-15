package com.easymoney.controller;

import com.easymoney.model.User;
import com.easymoney.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{uuid}")
    public User getUserById(@PathVariable String uuid) {
        return userService.getUserById(uuid);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getPhone(), user.getAge());
    }
}
