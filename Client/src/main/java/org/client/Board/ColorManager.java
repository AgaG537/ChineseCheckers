package org.client.Board;

import java.util.Map;
import javafx.scene.paint.Color;

/**
 * Utility class for managing colors associated with players.
 */
public class ColorManager {

  private static final Map<Integer, Color> DEFAULT_COLORS = Map.of(
      1, Color.BLACK,
      2, Color.RED,
      3, Color.GREEN,
      4, Color.WHITE,
      5, Color.YELLOW,
      6, Color.BLUE
  );

  private static final Map<Integer, Map<Integer, String>> GAME_PLAYER_COLORS = Map.of(
      2, Map.of(
          1, "BLACK",
          2, "WHITE"
      ),
      3, Map.of(
          1, "BLACK",
          2, "GREEN",
          3, "YELLOW"
      ),
      4, Map.of(
          1, "RED",
          2, "GREEN",
          3, "YELLOW",
          4, "BLUE"
      ),
      6, Map.of(
          1, "BLACK",
          2, "RED",
          3, "GREEN",
          4, "WHITE",
          5, "YELLOW",
          6, "BLUE"
      )
  );

  /**
   * Generates the default color for a given player based on their zone number.
   *
   * @param colorNum The color's number (1 to 6).
   * @return The {@link Color} corresponding to the zone's number.
   *     Returns Color.web("#ffa64d") for invalid numbers.
   */
  public static Color generateDefaultColor(int colorNum) {
    return DEFAULT_COLORS.getOrDefault(colorNum, Color.web("#ffa64d"));
  }

  /**
   * Retrieves the string representation of the default color for a given player in a specific game.
   *
   * @param numOfPlayers The number of players in the game.
   * @param playerNum The player's number.
   * @return The string representation of the player's color.
   */
  public static String getDefaultColorString(int numOfPlayers, int playerNum) {
    return GAME_PLAYER_COLORS.getOrDefault(numOfPlayers, Map.of())
        .getOrDefault(playerNum, String.valueOf(Color.web("#ffa64d")));
  }

}