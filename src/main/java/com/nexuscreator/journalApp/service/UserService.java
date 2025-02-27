package com.nexuscreator.journalApp.service;

import com.nexuscreator.journalApp.entity.User;
import com.nexuscreator.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        // ✅ Check if the username already exists before saving
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Username '" + user.getUserName() + "' is already taken. Please choose another.");
        }

        try {
            // ✅ Ensure new users have an `ObjectId`
            if (user.getId() == null) {
                user.setId(new ObjectId());
            }
            return userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Username '" + user.getUserName() + "' is already taken. Please choose another.");
        }
    }

    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
