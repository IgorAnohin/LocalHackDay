package ru.undeground.location;

import lombok.Data;

@Data
public class Location {

  String name;
  String viewLink;

  public Location(String name, String viewLink) {
    this.name = name;
    this.viewLink = viewLink;
  }

  @Override
  public String toString() {
    return "Location{" +
        "name='" + name + '\'' +
        ", viewLink='" + viewLink + '\'' +
        '}';
  }
}