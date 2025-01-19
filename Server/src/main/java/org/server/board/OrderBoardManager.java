package org.server.board;

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

  @Override
  protected void setupCell(Cell cell, int zoneNum, int defaultPlayerNum, int[] activeZoneNums) {
    int playerNum;
    if (activeZoneNums[zoneNum - 1] == 1) {
      playerNum = defaultPlayerNum;
    } else {
      playerNum = 0;
    }
    assignCellToZone(cell, zoneNum, playerNum);
  }

  private void distributePawns() {
    // Randomly distribute pawns in the middle of the board.
    System.out.println(seed);
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
            System.out.println("row: " + row + ", col: " + col + ", isOccupied: " + cells[row][col].isOccupied());
            i++;
          }
        }
      }
    }
  }

  @Override
  protected int getTargetPlayer(int numOfPlayers, int zoneNum) {
    return TargetPlayerHandler.getTargetPlayerNum(numOfPlayers, zoneNum);
  }
}
