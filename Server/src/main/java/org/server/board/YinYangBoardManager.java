package org.server.board;

/**
 * Specialized board manager for "Yin-Yang" game mode.
 * Configures the board and validates gameplay for the Yin-Yang setup.
 */
public class YinYangBoardManager extends AbstractBoardManager {

  /**
   * Constructor for the YinYangBoardManager class.
   *
   * @param marblesPerPlayer The number of marbles assigned to each player.
   */
  public YinYangBoardManager(int marblesPerPlayer) {
    super(marblesPerPlayer,2);

    setupPlayerZones(2);
  }

  /**
   * Configures player zones specific to the "Yin-Yang" mode.
   *
   * @param numOfPlayers The number of players (always 2 for Yin-Yang mode).
   */
  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(2,boardWidth,boardHeight,playerZoneHeight);
    cells =  playerZoneFactory.addYinYangZones(cells);
  }

  /**
   * Checks for a winning condition in the "Yin-Yang" mode.
   *
   * @return The player number of the winner, or 0 if no winner is determined.
   */
  @Override
  public int checkWin() {
    return PlayerZoneFactory.checkZoneForWin(cells,"yinyang");
  }

}
