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
  public BoardManager(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);
  }

  /**
   * Configures player zones for a standard board game.
   *
   * @param numOfPlayers The number of players in the game.
   */
  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers, boardWidth, boardHeight, playerZoneHeight);
    this.cells = playerZoneFactory.addPlayerZones(cells);
  }

  /**
   * Checks for a winning condition on the board.
   *
   * @return The player number of the winner, or 0 if no winner is determined.
   */
  @Override
  public int checkWin() {
    return PlayerZoneFactory.checkZoneForWin(cells,"standard");
  }
}
