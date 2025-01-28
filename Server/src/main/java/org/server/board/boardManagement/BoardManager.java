package org.server.board.boardManagement;

import org.server.board.boardObjects.Cell;
import org.server.board.utilityHandlers.TargetPlayerHandler;
import org.server.board.utilityHandlers.TargetZoneHandler;

/**
 * Concrete implementation of AbstractBoardManager for standard gameplay.
 * Manages the initialization and logic for a standard game board.
 */
public class BoardManager extends AbstractBoardManager {

  /**
   * Constructor for the BoardManager class.
   *
   * @param marblesPerPlayer The number of marbles assigned to each player.
   * @param numOfPlayers     The number of players participating in the game.
   */
  public BoardManager(int marblesPerPlayer, int numOfPlayers, int seed) {
    super(marblesPerPlayer, numOfPlayers, seed);
    setupPlayerZones();
  }

  /**
   * Sets up the specified cell by assigning it to the appropriate zone and player.
   * This method overrides the abstract setupCell method to implement the specific behavior for the standard game.
   *
   * @param cell The cell to be set up.
   * @param zoneNum The zone number the cell belongs to.
   * @param defaultPlayerNum The default player number for the zone.
   * @param activeZoneNums An array of active zone numbers indicating which zones are active.
   */
  @Override
  public void setupCell(Cell cell, int zoneNum, int defaultPlayerNum, int[] activeZoneNums) {
    int playerNum;
    if (activeZoneNums[zoneNum - 1] == 1) {
      playerNum = defaultPlayerNum;
    } else {
      playerNum = 0;
    }
    assignCellToZone(cell, zoneNum, playerNum);
    assignPawnToCell(cell, playerNum);
  }

  /**
   * Retrieves the target player based on the number of players and the zone number.
   * This method is used to determine the player number who should be targeted for a specific zone.
   *
   * @param numOfPlayers The number of players in the game.
   * @param zoneNum The zone number to check.
   * @return The player number who is the target for the zone.
   */
  @Override
  protected int getTargetPlayer(int numOfPlayers, int zoneNum) {
    return TargetPlayerHandler.getOppositeTargetPlayerNum(numOfPlayers, zoneNum);
  }

  /**
   * Retrieves the destination point for the given player.
   *
   * @param playerNum The player's number.
   * @return An array representing the row and column of the player's destination point.
   */
  @Override
  public int[] getDestinationPoint(int playerNum) {
    int counter = 0;
    int zoneNum = 0;
    while (counter != playerNum) {
      if (activeZoneNums[zoneNum] == 1) {
        counter++;
      }
      zoneNum++;
    }
    return playerZonesEdgePoints[(zoneNum - 1 + 3) % 6];
  }

  /**
   * Retrieves the destination zone number for the given player.
   *
   * @param playerNum The player's number.
   * @return The destination zone number.
   */
  @Override
  public int getDestinationZoneNum(int playerNum) {
    return TargetZoneHandler.getOppositeTargetZoneNum(numOfPlayers, playerNum);
  }
}
