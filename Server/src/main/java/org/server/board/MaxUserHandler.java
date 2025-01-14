package org.server.board;

public class MaxUserHandler {
  public static int handleMaxUsers (int maxUsers, String variant) {
    switch (variant) {
      case "yinyang":
        return 2;
      default:
        return maxUsers;
    }
  }
}
