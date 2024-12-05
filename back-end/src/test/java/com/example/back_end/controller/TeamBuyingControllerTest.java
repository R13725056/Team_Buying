package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.service.TeamBuyingService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class TeamBuyingControllerTest {

  @InjectMocks private TeamBuyingController teamBuyingController;

  @Mock private TeamBuyingService teamBuyingService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllTeamBuyings_Success() {
    // Arrange
    List<Map<String, Object>> mockTeamBuyings =
        List.of(Map.of("id", 1, "name", "TeamBuying1"), Map.of("id", 2, "name", "TeamBuying2"));

    when(teamBuyingService.getAllTeamBuyings()).thenReturn(mockTeamBuyings);

    // Act
    ResponseEntity<Object> response = teamBuyingController.getAllTeamBuyings();

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockTeamBuyings, response.getBody());
    verify(teamBuyingService, times(1)).getAllTeamBuyings();
  }

  @Test
  void testGetAllTeamBuyings_InternalServerError() {
    // Arrange
    when(teamBuyingService.getAllTeamBuyings()).thenReturn(null);

    // Act
    ResponseEntity<Object> response = teamBuyingController.getAllTeamBuyings();

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(
        Map.of("error", "Internal Server Error", "message", "not found any teambuying"),
        response.getBody());
    verify(teamBuyingService, times(1)).getAllTeamBuyings();
  }
}
