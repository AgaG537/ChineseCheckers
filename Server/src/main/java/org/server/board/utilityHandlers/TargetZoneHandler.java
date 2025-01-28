package org.server.board.utilityHandlers;

import java.util.Map;

/**
 * Utility class for handling target zones in the game, including calculating
 * both target and opposite target zones based on the number of players and player numbers.
 */
public class TargetZoneHandler {

  private static final Map<Integer, Map<Integer, Integer>> OPPOSITE_TARGET_ZONE_NUMBERS = Map.of(
      2, Map.of(
          1, 4,
          2, 1
      ),
      3, Map.of(
          1, 4,
          2, 6,
          3, 2
      ),
      4, Map.of(
          1, 5,
          2, 6,
          3, 2,
          4, 3
      ),
      6, Map.of(
          1, 4,
          2, 5,
          3, 6,
          4, 1,
          5, 2,
          6, 3
      )
  );

  private static final Map<Integer, Map<Integer, Integer>> TARGET_ZONE_NUMBERS = Map.of(
      2, Map.of(
          1, 1,
          2, 4
      ),
      3, Map.of(
          1, 1,
          2, 3,
          3, 5
      ),
      4, Map.of(
          1, 2,
          2, 3,
          3, 5,
          4, 6
      ),
      6, Map.of(
          1, 1,
          2, 2,
          3, 3,
          4, 4,
          5, 5,
          6, 6
      )
  );

  /**
   * Retrieves the target zone number for a given player in a game with a specific number of players.
   *
   * @param numOfPlayers The total number of players in the game.
   * @param playerNum The player's number.
   * @return The target zone number for the player.
   */
  public static int getTargetZoneNum(int numOfPlayers, int playerNum) {
    return TARGET_ZONE_NUMBERS.get(numOfPlayers).getOrDefault(playerNum, 0);
  }

  /**
   * Retrieves the opposite target zone number for a given player in a game with a specific number of players.
   *
   * @param numOfPlayers The total number of players in the game.
   * @param playerNum The player's number.
   * @return The opposite target zone number for the player.
   */
  public static int getOppositeTargetZoneNum(int numOfPlayers, int playerNum) {
    return OPPOSITE_TARGET_ZONE_NUMBERS.get(numOfPlayers).getOrDefault(playerNum, 0);
  }

}
