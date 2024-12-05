package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.dto.OrderFormRequest;
import com.example.back_end.service.OrderFormService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class OrderFormControllerTest {

  @InjectMocks private OrderFormController orderFormController;

  @Mock private OrderFormService orderFormService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateOrderForm() {
    // Arrange
    OrderFormRequest mockRequest = new OrderFormRequest();

    // Act
    ResponseEntity<String> response = orderFormController.createOrderForm(mockRequest);

    // Assert
    assertEquals(201, response.getStatusCodeValue());
    assertEquals("Order form created successfully!", response.getBody());
    verify(orderFormService, times(1)).createOrderForm(mockRequest);
  }

  @Test
  void testGetOrderStatus() {
    // Arrange
    int hostformId = 1;
    int participantFormId = 2;
    Map<String, Object> mockStatus = Map.of("status", "pending");
    when(orderFormService.getOrderStatus(hostformId, participantFormId)).thenReturn(mockStatus);

    // Act
    ResponseEntity<Map<String, Object>> response =
        orderFormController.getOrderStatus(participantFormId, hostformId);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(mockStatus, response.getBody());
    verify(orderFormService, times(1)).getOrderStatus(hostformId, participantFormId);
  }

  @Test
  void testGetOrderList() {
    // Arrange
    int hostformId = 1;
    List<Map<String, Object>> mockOrderList =
        List.of(Map.of("item", "Tea", "quantity", 2), Map.of("item", "Coffee", "quantity", 1));
    when(orderFormService.getOrderList(hostformId)).thenReturn(mockOrderList);

    // Act
    ResponseEntity<Map<String, Object>> response = orderFormController.getOrderList(hostformId);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(Map.of("orderList", mockOrderList), response.getBody());
    verify(orderFormService, times(1)).getOrderList(hostformId);
  }

  @Test
  void testGetMenuImage() {
    // Arrange
    int hostformId = 1;
    String mockImage = "base64EncodedImageString";
    when(orderFormService.getMenuImage(hostformId)).thenReturn(mockImage);

    // Act
    ResponseEntity<Map<String, String>> response = orderFormController.getMenuImage(hostformId);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(Map.of("image", mockImage), response.getBody());
    verify(orderFormService, times(1)).getMenuImage(hostformId);
  }
}
