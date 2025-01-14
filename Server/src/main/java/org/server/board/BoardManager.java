package org.server.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardManager extends AbstractBoardManager {

  /**
   * Constructor for the BoardManager class.
   *
   * @param marblesPerPlayer The number of marbles per player.
   * @param numOfPlayers     The number of players in the game.
   */
  public BoardManager(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);
  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers,boardWidth,boardHeight,playerZoneHeight);
    this.cells = playerZoneFactory.addPlayerZones(cells);
  }

  @Override
  public int checkWin() {
    return PlayerZoneFactory.checkZoneForWin(cells,"standard");
  }
}
