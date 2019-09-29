package com.example.mankind.Entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Collection;

/**
 * The type Users.
 */
@IgnoreExtraProperties
public class Users implements Serializable {
  private String username;
  private String password;
  private String type;
  private int goal;

  /**
   * Instantiates a new Users.
   */
  public Users() {
  }

  /**
   * Gets username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets type.
   *
   * @param type the type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets goal.
   *
   * @return the goal
   */
  public int getGoal() {
    return goal;
  }

  /**
   * Sets goal.
   *
   * @param goal the goal
   */
  public void setGoal(int goal) {
    this.goal = goal;
  }
}
