package org.server.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderBoardManager extends AbstractBoardManager {


  /**
   * Constructor for the Board class.
   *
   * @param marblesPerPlayer The number of marbles per player.
   */
  public OrderBoardManager(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);

  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers,boardWidth,boardHeight,playerZoneHeight);
    cells = playerZoneFactory.addOrderPlayerZones(numOfPlayers,boardWidth,boardHeight,playerZoneHeight,cells);
  }

  @Override
  public int checkWin() {
    return PlayerZoneFactory.checkZoneForWin(cells,"order");
  }
}
