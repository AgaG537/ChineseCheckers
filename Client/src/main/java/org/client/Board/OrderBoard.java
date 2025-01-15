package org.client.Board;

public class OrderBoard extends AbstractBoard {
  public OrderBoard(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);
  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers,boardWidth,boardHeight,playerZoneHeight);
    cells =  playerZoneFactory.addOrderPlayerZones(cells);
  }
}