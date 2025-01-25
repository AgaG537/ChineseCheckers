package org.server.board.utilityHandlers;

/**
 * Utility class to handle the maximum number of users allowed in the game
 * based on the selected variant.
 */
public class MaxUserHandler {

  /**
   * Determines the maximum number of users allowed for the given game variant.
   *
   * @param maxUsers The default maximum number of users.
   * @param variant  The game variant (e.g., "standard", "order", "yinyang").
   * @return The adjusted maximum number of users for the specified variant.
   */
  public static int handleMaxUsers(int maxUsers, String variant) {
    if (variant.equals("yinyang")) {
      if (maxUsers > 1) {
        return 2;
      }
    }
    return maxUsers;
  }
}
