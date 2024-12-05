package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.dto.*;
import com.example.back_end.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class UserControllerTest {

  @InjectMocks private UserController userController;

  @Mock private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // Initialize mocks
  }

  @Test
  void testGetUserInfo() {
    // Arrange
    int userId = 1;
    UserInfoDto mockUserInfo = new UserInfoDto();
    when(userService.getUserInfoById(userId)).thenReturn(mockUserInfo);

    // Act
    ResponseEntity<?> response = userController.getUserInfo(userId);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(mockUserInfo, response.getBody());
    verify(userService, times(1)).getUserInfoById(userId);
  }

  @Test
  void testUpdateUserInfo() {
    // Arrange
    int userId = 1;
    UserInfoDto userInfoDto = new UserInfoDto();
    when(userService.updateUserInfo(userId, userInfoDto)).thenReturn(true);

    // Act
    ResponseEntity<String> response = userController.updateUserInfo(userId, userInfoDto);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("User information updated successfully.", response.getBody());
    verify(userService, times(1)).updateUserInfo(userId, userInfoDto);
  }

  @Test
  void testGetHistoryList() throws Exception {
    // Arrange
    int userId = 1;
    List<UserHistoryDto.HostHistory> mockHostHistory = Collections.emptyList();
    List<UserHistoryDto.ParticipantHistory> mockParticipantHistory = Collections.emptyList();
    when(userService.getHostHistoryByUserId(userId)).thenReturn(mockHostHistory);
    when(userService.getParticipantHistoryByUserId(userId)).thenReturn(mockParticipantHistory);

    UserHistoryDto expected = new UserHistoryDto();
    expected.setHost(mockHostHistory);
    expected.setParticipant(mockParticipantHistory);

    // Act
    ResponseEntity<?> response = userController.getHistoryList(userId);

    // Assert
    ObjectMapper objectMapper = new ObjectMapper();
    String expectedJson = objectMapper.writeValueAsString(expected);
    String actualJson = objectMapper.writeValueAsString(response.getBody());

    assertEquals(expectedJson, actualJson); // 比較 JSON 字串
    verify(userService, times(1)).getHostHistoryByUserId(userId);
    verify(userService, times(1)).getParticipantHistoryByUserId(userId);
  }

  @Test
  void testGetNowHosting() {
    // Arrange
    int userId = 1;
    List<UserHistoryDto.HostHistory> mockNowHosting = Collections.emptyList();
    when(userService.getNowHostingByUserId(userId)).thenReturn(mockNowHosting);

    // Act
    ResponseEntity<List<UserHistoryDto.HostHistory>> response =
        userController.getNowHosting(userId);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(mockNowHosting, response.getBody());
    verify(userService, times(1)).getNowHostingByUserId(userId);
  }

  @Test
  void testGetNowBuying() {
    // Arrange
    int userId = 1;
    List<UserHistoryDto.ParticipantHistory> mockNowBuying = Collections.emptyList();
    when(userService.getNowBuyingByUserId(userId)).thenReturn(mockNowBuying);

    // Act
    ResponseEntity<List<UserHistoryDto.ParticipantHistory>> response =
        userController.getNowBuying(userId);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(mockNowBuying, response.getBody());
    verify(userService, times(1)).getNowBuyingByUserId(userId);
  }

  @Test
  void testGetReviewList() {
    // Arrange
    int userId = 1;
    List<ReviewListDto> mockReviewList = Collections.emptyList();
    when(userService.getReviewListByUserId(userId)).thenReturn(mockReviewList);

    // Act
    List<ReviewListDto> response = userController.getReviewList(userId);

    // Assert
    assertEquals(mockReviewList, response);
    verify(userService, times(1)).getReviewListByUserId(userId);
  }

  @Test
  void testGetReviews() {
    // Arrange
    int hostFormId = 1;
    List<ReviewDto> mockReviews = Collections.emptyList();
    when(userService.getReviewByHostFormId(hostFormId)).thenReturn(mockReviews);

    // Act
    List<ReviewDto> response = userController.getReviews(hostFormId);

    // Assert
    assertEquals(mockReviews, response);
    verify(userService, times(1)).getReviewByHostFormId(hostFormId);
  }
}
