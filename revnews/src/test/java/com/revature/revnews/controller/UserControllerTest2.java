package com.revature.revnews.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.revature.revnews.entity.User;
import com.revature.revnews.exception.UserRegistrationException;
import com.revature.revnews.service.UserService;

@SpringBootTest
public class UserControllerTest2 {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @Test
  public void testRegisterUser() throws UserRegistrationException {
    // Arrange
    User user = new User("test@email.com", "password", "John", "Doe"); 
    
    when(userService.registerUser(user)).thenReturn(user);

    // Act
    ResponseEntity response = userController.registerUser(user);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("User registration successful.", response.getBody());
    
    verify(userService, times(1)).registerUser(user);
  }
  
  
  @Test
  public void testLoginUser() {
    
    User user = new User("test@email.com", "password");
    when(userService.loginUser(user)).thenReturn(user);

    ResponseEntity response = userController.loginUser(user);

   
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(user, response.getBody());
    verify(userService, times(1)).loginUser(user);
  }

}
