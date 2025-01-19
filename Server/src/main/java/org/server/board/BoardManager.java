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
}
