package org.server.board;

import java.util.Arrays;
import java.util.Random;

/**
 * Specialized board manager for "Yin-Yang" game mode.
 * Configures the board and validates gameplay for the Yin-Yang setup.
 */
public class YinYangBoardManager extends AbstractBoardManager {

  private final int[] playerZoneNums;

  /**
   * Constructor for the YinYangBoardManager class.
   *
   * @param marblesPerPlayer The number of marbles assigned to each player.
   */
  public YinYangBoardManager(int marblesPerPlayer, int seed) {
    super(marblesPerPlayer, 2, seed);
    playerZoneNums = chooseRandomZones();
    setupPlayerZones();
  }

  /**
   * Randomly selects two player zones and ensures they are not the same or invalid.
   * This method assigns the selected zones to the two players.
   *
   * @return An array containing the two randomly selected zones for the players.
   */
  private int[] chooseRandomZones() {
    Random random = new Random(seed);

    int player1ZoneNum = random.nextInt(6);
    int player2ZoneNum = random.nextInt(6);
    while (player1ZoneNum == player2ZoneNum || player1ZoneNum == 0 || player2ZoneNum == 3) {
      player1ZoneNum = random.nextInt(6);
      player2ZoneNum = random.nextInt(6);
    }

    Arrays.fill(activeZoneNums, 0);
    activeZoneNums[player1ZoneNum] = activeZoneNums[player2ZoneNum] = 1;

    return new int[]{player1ZoneNum, player2ZoneNum};
  }

  /**
   * Sets up the specified cell by assigning it to the appropriate zone and player.
   * This method overrides the abstract setupCell method to implement the behavior for the "Yin-Yang" game variant.
   *
   * @param cell The cell to be set up.
   * @param zoneNum The zone number the cell belongs to.
   * @param defaultPlayerNum The default player number for the zone.
   * @param activeZoneNums An array of active zone numbers indicating which zones are active.
   */
  @Override
  public void setupCell(Cell cell, int zoneNum, int defaultPlayerNum, int[] activeZoneNums) {
    if (activeZoneNums[zoneNum - 1] == 1) {
      int playerNum;
      if (zoneNum - 1 == playerZoneNums[0]) {
        playerNum = 1;
      } else {
        playerNum = 2;
      }
      assignCellToZone(cell, zoneNum, playerNum);
      assignPawnToCell(cell, playerNum);
    }
  }

  /**
   * Retrieves the target player based on the zone number.
   * This method is used to determine the player number who should be targeted for a specific zone in the "Yin-Yang" game mode.
   *
   * @param numOfPlayers The number of players in the game.
   * @param zoneNum The zone number to check.
   * @return The player number who is the target for the zone.
   */
  @Override
  protected int getTargetPlayer(int numOfPlayers, int zoneNum) {
    if (zoneNum - 1 == playerZoneNums[0]) {
      return 2;
    } else if (zoneNum - 1 == playerZoneNums[1]) {
      return 1;
    }
    return 0;
  }

}
