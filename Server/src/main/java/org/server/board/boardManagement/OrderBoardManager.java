package org.server.board.boardManagement;

import org.server.board.boardObjects.Cell;
import org.server.board.utilityHandlers.TargetPlayerHandler;
import org.server.board.utilityHandlers.TargetZoneHandler;
import java.util.Random;

/**
 * Specialized board manager for "Order Out of Chaos" game mode.
 * Configures and validates gameplay for ordered player zones.
 */
public class OrderBoardManager extends AbstractBoardManager {

  /**
   * Constructor for the OrderBoardManager class.
   *
   * @param marblesPerPlayer The number of marbles per player.
   * @param numOfPlayers     The number of players in the game.
   */
  public OrderBoardManager(int marblesPerPlayer, int numOfPlayers, int seed) {
    super(marblesPerPlayer, numOfPlayers, seed);
    setupPlayerZones();
    distributePawns();
  }

  /**
   * Sets up the specified cell by assigning it to the appropriate zone.
   * This method overrides the abstract setupCell method to implement the behavior for the "Order" game variant.
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
  }

  /**
   * Randomly distributes pawns on the board for players, placing them in valid empty cells.
   * This method is specific to the "Order" game variant and handles pawn placement.
   */
  private void distributePawns() {
    // Randomly distribute pawns in the middle of the board.
    Random random = new Random(seed);
    int player = 0;
    for (int j : activeZoneNums) {
      if (j != 0) {
        player++;
        int i = 0;
        while (i < numOfCellsPerZone) {
          int row = random.nextInt(boardHeight);
          int col = random.nextInt(boardWidth);

          if (cells[row][col].getPawn() == null && cells[row][col].getZoneNum() == 0 && cells[row][col].isInsideBoard()) {
            assignPawnToCell(cells[row][col], player);
            i++;
          }
        }
      }
    }
  }

  /**
   * Retrieves the target player based on the number of players and the zone number.
   * This method is used to determine the player number who should be targeted for a specific zone in the "Order" game mode.
   *
   * @param numOfPlayers The number of players in the game.
   * @param zoneNum The zone number to check.
   * @return The player number who is the target for the zone.
   */
  @Override
  protected int getTargetPlayer(int numOfPlayers, int zoneNum) {
    return TargetPlayerHandler.getTargetPlayerNum(numOfPlayers, zoneNum);
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
    return playerZonesEdgePoints[zoneNum - 1];
  }

  /**
   * Retrieves the destination zone number for the given player.
   *
   * @param playerNum The player's number.
   * @return The destination zone number.
   */
  @Override
  public int getDestinationZoneNum(int playerNum) {
    return TargetZoneHandler.getTargetZoneNum(numOfPlayers, playerNum);
  }
}
