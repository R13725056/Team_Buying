package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.dto.FeedbackRequest;
import com.example.back_end.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class FeedbackControllerTest {

  @InjectMocks private FeedbackController feedbackController;

  @Mock private FeedbackService feedbackService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateFeedback() {
    // Arrange
    FeedbackRequest feedbackRequest = new FeedbackRequest();
    feedbackRequest.setUserId("1");
    feedbackRequest.setHostId("2");
    feedbackRequest.setHostFormId("3");
    feedbackRequest.setScore(5);
    feedbackRequest.setContent("Great service!");

    // Act
    ResponseEntity<Object> response = feedbackController.createFeedback(feedbackRequest);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode()); // 檢查回應狀態
    assertEquals(feedbackRequest, response.getBody()); // 檢查回應內容
    verify(feedbackService, times(1)).saveFeedback(feedbackRequest); // 確保 service 方法被呼叫一次
  }
}
