package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.model.LoginRequest;
import com.example.back_end.model.LoginResponse;
import com.example.back_end.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AuthControllerTest {

  @InjectMocks private AuthController authController;

  @Mock private AuthService authService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLogin_Success() {
    // Arrange
    LoginRequest request = new LoginRequest();
    request.setUserName("testUser");
    request.setPassword("password123");

    LoginResponse mockResponse =
        LoginResponse.builder().token("mockToken").userName("testUser").userId(1).build();

    when(authService.login(request)).thenReturn(mockResponse);

    // Act
    ResponseEntity<?> response = authController.login(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponse, response.getBody());
    verify(authService, times(1)).login(request);
  }

  @Test
  void testLogin_Failure() {
    // Arrange
    LoginRequest request = new LoginRequest();
    request.setUserName("testUser");
    request.setPassword("wrongPassword");

    when(authService.login(request)).thenThrow(new RuntimeException("Invalid credentials"));

    // Act
    ResponseEntity<?> response = authController.login(request);

    // Assert
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid credentials: Invalid credentials", response.getBody());
    verify(authService, times(1)).login(request);
  }

  @Test
  void testTestEndpoint() {
    // Act
    ResponseEntity<String> response = authController.test();

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("API is working!", response.getBody());
  }
}
