package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.service.ManageService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ManageControllerTest {

  @InjectMocks private ManageController manageController;

  @Mock private ManageService manageService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testUpdateTransferStatus_Success() {
    // Arrange
    int orderId = 1;
    Map<String, Integer> requestBody = Map.of("paymentStatus", 1);
    when(manageService.updateTransferStatus(orderId, 1)).thenReturn(true);

    // Act
    ResponseEntity<String> response = manageController.updateTransferStatus(orderId, requestBody);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Transfer status updated successfully.", response.getBody());
    verify(manageService, times(1)).updateTransferStatus(orderId, 1);
  }

  @Test
  void testUpdateTransferStatus_Failure() {
    // Arrange
    int orderId = 1;
    Map<String, Integer> requestBody = Map.of("paymentStatus", 1);
    when(manageService.updateTransferStatus(orderId, 1)).thenReturn(false);

    // Act
    ResponseEntity<String> response = manageController.updateTransferStatus(orderId, requestBody);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Failed to update transfer status.", response.getBody());
    verify(manageService, times(1)).updateTransferStatus(orderId, 1);
  }

  @Test
  void testUpdateOrderStatus_Success() {
    // Arrange
    int orderId = 2;
    Map<String, Integer> requestBody = Map.of("status", 1);
    when(manageService.updateOrderStatus(orderId, 1)).thenReturn(true);

    // Act
    ResponseEntity<String> response = manageController.updateOrderStatus(orderId, requestBody);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Order status updated successfully.", response.getBody());
    verify(manageService, times(1)).updateOrderStatus(orderId, 1);
  }

  @Test
  void testUpdateOrderStatus_Failure() {
    // Arrange
    int orderId = 2;
    Map<String, Integer> requestBody = Map.of("status", 1);
    when(manageService.updateOrderStatus(orderId, 1)).thenReturn(false);

    // Act
    ResponseEntity<String> response = manageController.updateOrderStatus(orderId, requestBody);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Failed to update order status.", response.getBody());
    verify(manageService, times(1)).updateOrderStatus(orderId, 1);
  }

  @Test
  void testFinishTeamBuying_Success() {
    // Arrange
    int hostformId = 3;
    when(manageService.finishTeamBuying(hostformId)).thenReturn(true);

    // Act
    ResponseEntity<String> response = manageController.finishTeamBuying(hostformId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Team buying finished successfully.", response.getBody());
    verify(manageService, times(1)).finishTeamBuying(hostformId);
  }

  @Test
  void testFinishTeamBuying_Failure() {
    // Arrange
    int hostformId = 3;
    when(manageService.finishTeamBuying(hostformId)).thenReturn(false);

    // Act
    ResponseEntity<String> response = manageController.finishTeamBuying(hostformId);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Failed to finish team buying.", response.getBody());
    verify(manageService, times(1)).finishTeamBuying(hostformId);
  }
}
