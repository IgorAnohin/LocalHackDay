package ru.undeground;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

@Wither
@Data
@AllArgsConstructor
public class User {

  private String userId;
  private String name;
}
