package org.client.Board;

public class StandardBoard extends AbstractBoard {
  public StandardBoard(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);
  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    cells = PlayerZoneFactory.addPlayerZones(numOfPlayers, boardWidth, boardHeight, playerZoneHeight, cells);
  }
}