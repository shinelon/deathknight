package com.shinelon.deathknight.bean;

public class Tag {

  private String name;
  /***
   * for jackson deserialize
   */
  public Tag() {}

  public Tag(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Tag [name=" + name + "]";
  }
}
