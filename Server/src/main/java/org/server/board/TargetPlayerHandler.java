package org.server.board;

import java.util.Map;

public class TargetPlayerHandler {

  private static final Map<Integer, Map<Integer, Integer>> OPPOSITE_TARGET_PLAYER_NUMBERS = Map.of(
      2, Map.of(
          1, 2,
          4, 1
      ),
      3, Map.of(
          2, 3,
          6, 2,
          4, 1
      ),
      4, Map.of(
          3, 4,
          2, 3,
          6, 2,
          5, 1
      ),
      6, Map.of(
          3, 6,
          2, 5,
          1, 4,
          6, 3,
          5, 2,
          4, 1
      )
  );

  private static final Map<Integer, Map<Integer, Integer>> TARGET_PLAYER_NUMBERS = Map.of(
      2, Map.of(
          1, 1,
          4, 2
      ),
      3, Map.of(
          1, 1,
          3, 2,
          5, 3
      ),
      4, Map.of(
          2, 1,
          3, 2,
          5, 3,
          6, 4
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
   * Retrieves the player number corresponding to the target zone in a standard game.
   *
   * @param zoneNum The zone number for which to retrieve the target player number.
   * @return The player number of the target zone.
   */
  public static int getTargetPlayerNum(int numOfPlayers, int zoneNum) {
    return TARGET_PLAYER_NUMBERS.get(numOfPlayers).getOrDefault(zoneNum, 0);
  }

  /**
   * Retrieves the player number corresponding to the opposite target zone in a standard game.
   *
   * @param zoneNum The zone number for which to retrieve the opposite target player number.
   * @return The player number of the opposite target zone.
   */
  public static int getOppositeTargetPlayerNum(int numOfPlayers, int zoneNum) {
    return OPPOSITE_TARGET_PLAYER_NUMBERS.get(numOfPlayers).getOrDefault(zoneNum, 0);
  }

}
