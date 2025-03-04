package com.nexuscreator.journalApp.controller;

import com.nexuscreator.journalApp.entity.User;
import com.nexuscreator.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all") public List<User> getAllUsers(){
        return  userService.getAll();
    }

    // ✅ Get user by username
    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        Optional<User> user = userService.getUserByUserName(userName);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // ✅ Return User object with 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // ✅ Return String message with 404
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // ✅ 201 Created
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // ✅ 409 Conflict
        }
    }

    // ✅ Update user by username
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody User user) {
        Optional<User> optionalUser = userService.getUserByUserName(userName);

        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User userInDb = optionalUser.get();

        // Update only provided fields
        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            userInDb.setUserName(user.getUserName()); // Ensure username update is intentional
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userInDb.setPassword(user.getPassword());
        }

        User updatedUser = userService.saveUser(userInDb);
        return ResponseEntity.ok(updatedUser);
    }

}
