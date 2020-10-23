package com.shinelon.deathknight.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
  private String name;

  @JsonProperty(value = "phoneNo")
  private String mobile;

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

  @Override
  public String toString() {
    return "User [name=" + name + ", mobile=" + mobile + "]";
  }
}
