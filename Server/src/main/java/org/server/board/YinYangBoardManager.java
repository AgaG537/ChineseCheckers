package org.server.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YinYangBoardManager extends AbstractBoardManager {

  /**
   * Constructor for the Board class.
   *
   * @param marblesPerPlayer The number of marbles per player.
   */
  public YinYangBoardManager(int marblesPerPlayer) {
    super(marblesPerPlayer,2);

    setupPlayerZones(2);
  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(2,boardWidth,boardHeight,playerZoneHeight);
    cells =  playerZoneFactory.addYinYangZones(boardWidth,boardHeight,playerZoneHeight,cells);
  }

}
