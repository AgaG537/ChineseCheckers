package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Utility class for managing colors associated with players.
 */
public class ColorManager {

  /**
   * Generates the default color for a given player based on their player number.
   *
   * @param playerNum The player's number (1 to 6).
   * @return The {@link Color} corresponding to the player's number. Returns {@link Color#GREY} for invalid numbers.
   */
  public static Color generateDefaultColor(int playerNum) {
    switch (playerNum) {
      case 1:
        return Color.BLACK;
      case 2:
        return Color.RED;
      case 3:
        return Color.GREEN;
      case 4:
        return Color.WHITE;
      case 5:
        return Color.YELLOW;
      case 6:
        return Color.BLUE;
    }
    return Color.GREY;
  }

  /**
   * Retrieves the string representation of the default color for a given player.
   *
   * @param playerNum The player's number (1 to 6).
   * @return The string representation of the player's color. Returns "GREY" for invalid numbers.
   */
  public static String getDefaultColorString(int playerNum) {
    switch (playerNum) {
      case 1:
        return "BLACK";
      case 2:
        return "RED";
      case 3:
        return "GREEN";
      case 4:
        return "WHITE";
      case 5:
        return "YELLOW";
      case 6:
        return "BLUE";
    }
    return "GREY";
  }

}
