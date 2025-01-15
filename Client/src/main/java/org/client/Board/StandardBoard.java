package org.client.Board;

/**
 * Represents a Standard game board with general player zone setup.
 */
public class StandardBoard extends AbstractBoard {

  /**
   * Constructs a StandardBoard with the specified number of marbles per player and players.
   *
   * @param marblesPerPlayer Number of marbles allocated to each player.
   * @param numOfPlayers     Number of players in the game.
   */
  public StandardBoard(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);
  }

  /**
   * Sets up the player zones for the standard game variant.
   *
   * @param numOfPlayers Number of players in the game.
   */
  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers, boardWidth, boardHeight, playerZoneHeight);
    cells =  playerZoneFactory.addPlayerZones(cells);
  }
}