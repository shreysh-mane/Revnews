package com.revature.revnews.repository;

import com.revature.revnews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.firstName = ?2, u.lastName = ?3, u.email = ?4, u.password = ?5 WHERE u.email = ?1")
    int updateUserProfile(String email, String firstName, String lastName, String updatedEmail, String updatedPassword);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.email = ?1")
    void deleteUserByEmail(String email);
}
