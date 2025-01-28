package org.server.board.boardManagement;

import java.util.Arrays;
import java.util.Random;
import org.server.board.boardObjects.Cell;

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
    while (player1ZoneNum == player2ZoneNum) {
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

  /**
   * Retrieves the destination point for the given player.
   *
   * @param playerNum The player's number.
   * @return An array representing the row and column of the player's destination point.
   */
  @Override
  public int[] getDestinationPoint(int playerNum) {
    if (playerNum == 1) {
      return playerZonesEdgePoints[playerZoneNums[1]];
    } else {
      return playerZonesEdgePoints[playerZoneNums[0]];
    }
  }

  /**
   * Retrieves the destination zone number for the given player.
   *
   * @param playerNum The player's number.
   * @return The destination zone number.
   */
  @Override
  public int getDestinationZoneNum(int playerNum) {
    if (playerNum == 1) {
      return playerZoneNums[1] + 1;
    } else if (playerNum == 2) {
      return playerZoneNums[0] + 1;
    }
    return 0;
  }
}
