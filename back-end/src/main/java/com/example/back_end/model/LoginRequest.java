package com.example.back_end.model;

import lombok.Data;

@Data
public class LoginRequest {
  private String userName;
  private String password;

  public static LoginResponse of(String token, String userName, int expiration) {
    return new LoginResponse(token, userName, expiration);
  }
}
