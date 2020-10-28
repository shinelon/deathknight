package com.shinelon.deathknight.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class User {

  private String name;

  @JsonProperty(value = "phoneNo")
  private String mobile;

  @JsonProperty(access = Access.READ_ONLY)
  private String email;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @JsonIgnore
  private Boolean isAdmin;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  @Override
  public String toString() {
    return "User [name="
        + name
        + ", mobile="
        + mobile
        + ", email="
        + email
        + ", password="
        + password
        + ", isAdmin="
        + isAdmin
        + "]";
  }
}
