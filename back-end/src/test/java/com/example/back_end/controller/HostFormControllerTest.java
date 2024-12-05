package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.dto.CreateHostFormDTO;
import com.example.back_end.entity.HostForm;
import com.example.back_end.service.HostFormService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class HostFormControllerTest {

  @InjectMocks private HostFormController hostFormController;

  @Mock private HostFormService hostFormService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateHostForm_Success() {
    // Arrange
    CreateHostFormDTO request = new CreateHostFormDTO();
    request.setTitle("Group Purchase");
    HostForm mockHostForm = new HostForm();
    mockHostForm.setId(1);

    when(hostFormService.createHostForm(request)).thenReturn(mockHostForm);

    // Act
    ResponseEntity<?> response = hostFormController.createHostForm(request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Map.of("id", 1), response.getBody());
    verify(hostFormService, times(1)).createHostForm(request);
  }

  @Test
  void testCreateHostForm_IllegalArgumentException() {
    // Arrange
    CreateHostFormDTO request = new CreateHostFormDTO();
    when(hostFormService.createHostForm(request))
        .thenThrow(new IllegalArgumentException("Invalid request"));

    // Act
    ResponseEntity<?> response = hostFormController.createHostForm(request);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid request", response.getBody());
    verify(hostFormService, times(1)).createHostForm(request);
  }

  @Test
  void testGetHostForm_Success() {
    // Arrange
    int id = 1;
    HostForm mockHostForm = new HostForm();
    mockHostForm.setId(id);

    when(hostFormService.findById(id)).thenReturn(Optional.of(mockHostForm));

    // Act
    ResponseEntity<?> response = hostFormController.getHostForm(id);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockHostForm, response.getBody());
    verify(hostFormService, times(1)).findById(id);
  }

  @Test
  void testGetHostForm_NotFound() {
    // Arrange
    int id = 1;
    when(hostFormService.findById(id)).thenReturn(Optional.empty());

    // Act
    ResponseEntity<?> response = hostFormController.getHostForm(id);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    verify(hostFormService, times(1)).findById(id);
  }

  @Test
  void testGetHostFormsByHostId_Success() {
    // Arrange
    int hostId = 1;
    List<HostForm> mockForms = List.of(new HostForm(), new HostForm());
    when(hostFormService.findByHostId(hostId)).thenReturn(mockForms);

    // Act
    ResponseEntity<?> response = hostFormController.getHostFormsByHostId(hostId);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockForms, response.getBody());
    verify(hostFormService, times(1)).findByHostId(hostId);
  }

  @Test
  void testUpdateHostForm_Success() {
    // Arrange
    int id = 1;
    CreateHostFormDTO request = new CreateHostFormDTO();
    request.setTitle("Updated Title");
    HostForm mockUpdatedHostForm = new HostForm();
    mockUpdatedHostForm.setId(id);

    when(hostFormService.updateHostForm(id, request)).thenReturn(mockUpdatedHostForm);

    // Act
    ResponseEntity<?> response = hostFormController.updateHostForm(id, request);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockUpdatedHostForm, response.getBody());
    verify(hostFormService, times(1)).updateHostForm(id, request);
  }

  @Test
  void testDeleteHostForm_Success() {
    // Arrange
    int id = 1;

    // Act
    ResponseEntity<?> response = hostFormController.deleteHostForm(id);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("團購表單已成功刪除", response.getBody());
    verify(hostFormService, times(1)).deleteHostForm(id);
  }

  @Test
  void testDeleteHostForm_NotFound() {
    // Arrange
    int id = 1;
    doThrow(new IllegalArgumentException("Host form not found"))
        .when(hostFormService)
        .deleteHostForm(id);

    // Act
    ResponseEntity<?> response = hostFormController.deleteHostForm(id);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Host form not found", response.getBody());
    verify(hostFormService, times(1)).deleteHostForm(id);
  }
}
