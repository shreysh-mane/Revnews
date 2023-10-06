    
package com.revature.revnews.controller;

import com.revature.revnews.entity.User;
import com.revature.revnews.exception.UserRegistrationException;
import com.revature.revnews.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            logger.info("Attempting to register user: {}", user.getEmail());
            User registeredUser = userService.registerUser(user);
            logger.info("User registration successful for email: {}", registeredUser.getEmail());
            return new ResponseEntity<>("User registration successful.", HttpStatus.CREATED);
        } catch (UserRegistrationException e) {
            logger.error("User registration failed: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("User registration failed due to an unexpected error.", e);
            return new ResponseEntity<>("User registration failed. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            logger.info("Attempting to login user: {}", user.getEmail());
            User loggedInUser = userService.loginUser(user);
            if (loggedInUser != null) {
                logger.info("User login successful for email: {}", loggedInUser.getEmail());
                return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
            } else {
                logger.error("Invalid email or password for user: {}", user.getEmail());
                return new ResponseEntity<>("Invalid email or password.", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("User login failed due to an unexpected error.", e);
            return new ResponseEntity<>("Login failed. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUserProfile(@PathVariable String email) {
        try {
            logger.info("Fetching user profile for email: {}", email);
            User userProfile = userService.getUserByEmail(email);
            if (userProfile != null) {
                logger.info("User profile retrieved for email: {}", userProfile.getEmail());
                return new ResponseEntity<>(userProfile, HttpStatus.OK);
            } else {
                logger.error("User not found for email: {}", email);
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Failed to retrieve user profile for email: {}", email, e);
            return new ResponseEntity<>("Failed to retrieve user profile.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{email}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String email, @RequestBody User user) {
        try {
            logger.info("Attempting to update user profile for email: {}", email);
            User updatedUser = userService.updateUserProfile(email, user);
            if (updatedUser != null) {
                logger.info("User profile updated successfully for email: {}", email);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                logger.error("Failed to update user profile for email: {}", email);
                return new ResponseEntity<>("Failed to update user profile. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Failed to update user profile for email: {}", email, e);
            return new ResponseEntity<>("Failed to update user profile. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUserAccount(@PathVariable String email) {
        try {
            logger.info("Attempting to delete user account for email: {}", email);
            userService.deleteUser(email);
            logger.info("User account deleted successfully for email: {}", email);
            return new ResponseEntity<>("User account deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to delete user account for email: {}", email, e);
            return new ResponseEntity<>("Failed to delete user account.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


    