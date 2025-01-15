package org.server.board;

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
  public OrderBoardManager(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);

  }

  /**
   * Configures player zones specific to the "Order Out of Chaos" mode.
   *
   * @param numOfPlayers The number of players in the game.
   */
  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers,boardWidth,boardHeight,playerZoneHeight);
    cells = playerZoneFactory.addOrderPlayerZones(cells);
  }

  /**
   * Checks for a winning condition in the "Order Out of Chaos" mode.
   *
   * @return The player number of the winner, or 0 if no winner is determined.
   */
  @Override
  public int checkWin() {
    return PlayerZoneFactory.checkZoneForWin(cells,"order");
  }
}
