package org.client.Board;

public class YinYangBoard extends AbstractBoard {
  public YinYangBoard(int marblesPerPlayer) {
    super(marblesPerPlayer, 0);
  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(2,boardWidth,boardHeight,playerZoneHeight);
    cells =  playerZoneFactory.addYinYangZones(cells);
  }
}