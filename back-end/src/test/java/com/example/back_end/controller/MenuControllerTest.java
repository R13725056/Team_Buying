package com.example.back_end.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.back_end.service.MenuService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class MenuControllerTest {

  @InjectMocks private MenuController menuController;

  @Mock private MenuService menuService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testInsertMenu_Success() {
    // Arrange
    Map<String, Object> requestBody =
        Map.of(
            "name", "Test Menu",
            "img", "base64ImageString",
            "products",
                List.of(
                    Map.of("productName", "Product1", "price", 100),
                    Map.of("productName", "Product2", "price", 200)));

    when(menuService.insertMenu(anyString(), anyString(), anyList())).thenReturn(true);

    // Act
    ResponseEntity<String> response = menuController.insertMenu(requestBody);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Insert Menu successfully.", response.getBody());
    verify(menuService, times(1)).insertMenu(eq("Test Menu"), eq("base64ImageString"), anyList());
  }

  @Test
  void testInsertMenu_Failure() {
    // Arrange
    Map<String, Object> requestBody =
        Map.of(
            "name", "Test Menu",
            "img", "base64ImageString",
            "products", List.of(Map.of("productName", "Product1", "price", 100)));

    when(menuService.insertMenu(anyString(), anyString(), anyList())).thenReturn(false);

    // Act
    ResponseEntity<String> response = menuController.insertMenu(requestBody);

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Failed to insert Menu.", response.getBody());
    verify(menuService, times(1)).insertMenu(eq("Test Menu"), eq("base64ImageString"), anyList());
  }

  @Test
  void testInsertMenu_InvalidRequest() {
    // Arrange
    Map<String, Object> requestBody =
        Map.of(
            "name", "Test Menu"
            // Missing "img" and "products"
            );

    // Act
    ResponseEntity<String> response = menuController.insertMenu(requestBody);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    verifyNoInteractions(menuService);
  }

  @Test
  void testGetMenu_Success() {
    // Arrange
    List<Map<String, Object>> mockMenus =
        List.of(
            Map.of("menuId", 1, "name", "Menu1", "products", List.of()),
            Map.of("menuId", 2, "name", "Menu2", "products", List.of()));

    when(menuService.getMenu()).thenReturn(mockMenus);

    // Act
    ResponseEntity<Object> response = menuController.getMenu();

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockMenus, response.getBody());
    verify(menuService, times(1)).getMenu();
  }

  @Test
  void testGetMenu_Failure() {
    // Arrange
    when(menuService.getMenu()).thenReturn(null);

    // Act
    ResponseEntity<Object> response = menuController.getMenu();

    // Assert
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Failed to get Menu.", response.getBody());
    verify(menuService, times(1)).getMenu();
  }
}
