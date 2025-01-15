package org.client.Board;

public class StandardBoard extends AbstractBoard {
  public StandardBoard(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);
  }

  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers,boardWidth,boardHeight,playerZoneHeight);
    cells =  playerZoneFactory.addPlayerZones(cells);
  }
}