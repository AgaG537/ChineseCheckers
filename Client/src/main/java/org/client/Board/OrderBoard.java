package org.client.Board;

public class OrderBoard extends AbstractBoard {
  public OrderBoard(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);
  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    cells = PlayerZoneFactory.addOrderPlayerZones(numOfPlayers, boardWidth, boardHeight, playerZoneHeight, cells);
  }
}