package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.service.NotifyService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class NotifyControllerTest {

  @InjectMocks private NotifyController notifyController;

  @Mock private NotifyService notifyService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testNotifyHost_Success() {
    // Arrange
    int hostFormId = 1;
    int userId = 123;
    String userName = "Test User";
    int hostId = 456;
    Map<String, Object> requestBody = Map.of("userId", userId, "userName", userName);

    when(notifyService.getHostId(hostFormId)).thenReturn(hostId);

    // Act
    ResponseEntity<String> response = notifyController.notifyHost(hostFormId, requestBody);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Notification sent to host ID: 456", response.getBody());
    verify(notifyService, times(1)).getHostId(hostFormId);
    verify(notifyService, times(1)).notifyHost(eq(hostId), anyString());
  }

  @Test
  void testNotifyHost_BadRequest_MissingFields() {
    // Arrange
    int hostFormId = 1;
    Map<String, Object> requestBody = Map.of("userId", 123); // Missing "userName"

    // Act
    ResponseEntity<String> response = notifyController.notifyHost(hostFormId, requestBody);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("userId & userName are required", response.getBody());
    verifyNoInteractions(notifyService);
  }

  @Test
  void testNotifyHost_HostNotFound() {
    // Arrange
    int hostFormId = 1;
    Map<String, Object> requestBody = Map.of("userId", 123, "userName", "Test User");

    when(notifyService.getHostId(hostFormId)).thenReturn(null);

    // Act
    ResponseEntity<String> response = notifyController.notifyHost(hostFormId, requestBody);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Host not found for hostFormId: 1", response.getBody());
    verify(notifyService, times(1)).getHostId(hostFormId);
    verifyNoMoreInteractions(notifyService);
  }
}
