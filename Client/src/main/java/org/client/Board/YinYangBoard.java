package org.client.Board;

public class YinYangBoard extends AbstractBoard {
  public YinYangBoard(int marblesPerPlayer) {
    super(marblesPerPlayer, 0);
  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    cells = PlayerZoneFactory.addYinYangZones(boardWidth, boardHeight, playerZoneHeight, cells);
  }
}