package com.Lotus.polyFood.payload.Response;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String refreshToken;
  private int id;
  private String username;
  private String email;
  private List<String> roles;

  public JwtResponse(String token, String refreshToken, int id, String username, String email, List<String> roles) {
    this.token = token;
    this.refreshToken = refreshToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String jwtToken) {
    this.token = jwtToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
