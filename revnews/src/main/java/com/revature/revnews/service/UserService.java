package com.revature.revnews.service;

import com.revature.revnews.entity.User;
import com.revature.revnews.exception.UserRegistrationException;
import com.revature.revnews.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) throws UserRegistrationException {
        try {
            logger.info("Attempting to register user: {}", user.getEmail());

            // Check if the email already exists
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new UserRegistrationException("Email already exists.");
            }

            user.setUserType("user"); // Set the user type as "user"
            User registeredUser = userRepository.save(user);
            logger.info("User registration successful for email: {}", registeredUser.getEmail());
            return registeredUser;
        } catch (Exception e) {
            logger.error("User registration failed due to an unexpected error.", e);
            throw new UserRegistrationException("User registration failed. Please try again later.");
        }
    }

    public User loginUser(User user) {
        try {
            logger.info("Attempting to login user: {}", user.getEmail());

            // Retrieve the user from the database by email
            User dbUser = userRepository.findByEmail(user.getEmail());

            // Check if the user exists and the password matches
            if (dbUser != null && user.getPassword().equals(dbUser.getPassword())) {
                dbUser.setPassword(null); // Remove the password before returning
                logger.info("User login successful for email: {}", dbUser.getEmail());
                return dbUser;
            } else {
                logger.error("Invalid email or password for user: {}", user.getEmail());
                return null; // Return null for invalid credentials
            }
        } catch (Exception e) {
            logger.error("User login failed due to an unexpected error.", e);
            return null; // Return null for unexpected errors
        }
    }

    public User getUserByEmail(String email) {
        try {
            logger.info("Fetching user by email: {}", email);
            User user = userRepository.findByEmail(email);
            if (user != null) {
                user.setPassword(null); // Remove the password before returning
                logger.info("User retrieved successfully by email: {}", email);
                return user;
            } else {
                logger.error("User not found for email: {}", email);
                return null; // Return null if user not found
            }
        } catch (Exception e) {
            logger.error("Failed to retrieve user by email: {}", email, e);
            return null; // Return null for unexpected errors
        }
    }

    public User updateUserProfile(String email, User updatedUser) {
        try {
            logger.info("Attempting to update user profile for email: {}", email);

            // Retrieve the user from the database by email
            User existingUser = userRepository.findByEmail(email);

            if (existingUser != null) {
                // Update user information based on the updatedUser object
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                existingUser.setPassword(updatedUser.getPassword());
                // You can add more fields to update as needed
                User updatedUserProfile = userRepository.save(existingUser);
                updatedUserProfile.setPassword(null); // Remove the password before returning
                logger.info("User profile updated successfully for email: {}", email);
                return updatedUserProfile;
            } else {
                logger.error("User not found for email: {}", email);
                return null; // Return null if user not found
            }
        } catch (Exception e) {
            logger.error("Failed to update user profile for email: {}", email, e);
            return null; // Return null for unexpected errors
        }
    }

    public void deleteUser(String email) {
        try {
            logger.info("Attempting to delete user account for email: {}", email);

            // Delete the user from the database by email
            userRepository.deleteUserByEmail(email);
            logger.info("User account deleted successfully for email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to delete user account for email: {}", email, e);
        }
    }
}



