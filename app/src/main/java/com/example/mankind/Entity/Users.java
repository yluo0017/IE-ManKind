package com.example.mankind.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Collection;

@IgnoreExtraProperties
public class Users implements Serializable {
  private String username;
  private String password;
  private String type;
  private int goal;

  public Users() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getGoal() {
    return goal;
  }

  public void setGoal(int goal) {
    this.goal = goal;
  }
}
