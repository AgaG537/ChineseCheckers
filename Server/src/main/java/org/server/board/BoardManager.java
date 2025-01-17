package org.server.board;

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

  @Override
  protected void setupCell(Cell cell, int zoneNum, int defaultPlayerNum, int[] activeZoneNums) {
    int playerNum;
    if (activeZoneNums[zoneNum - 1] == 1) {
      playerNum = defaultPlayerNum;
    } else {
      playerNum = 0;
    }
    assignCellToZone(cell, zoneNum, playerNum);
    assignPawnToCell(cell, playerNum);
  }

  @Override
  protected int getTargetPlayer(int numOfPlayers, int zoneNum) {
    return TargetPlayerHandler.getOppositeTargetPlayerNum(numOfPlayers, zoneNum);
  }
}
