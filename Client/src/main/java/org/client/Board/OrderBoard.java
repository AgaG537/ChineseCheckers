package org.client.Board;

/**
 * Represents an Order game board with specific player zone setup for Order variant rules.
 */
public class OrderBoard extends AbstractBoard {

  /**
   * Constructs an OrderBoard with the specified number of marbles per player and players.
   *
   * @param marblesPerPlayer Number of marbles allocated to each player.
   * @param numOfPlayers     Number of players in the game.
   */
  public OrderBoard(int marblesPerPlayer, int numOfPlayers) {
    super(marblesPerPlayer, numOfPlayers);
  }

  /**
   * Sets up the player zones for the Order game variant.
   *
   * @param numOfPlayers Number of players in the game.
   */
  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers, boardWidth, boardHeight, playerZoneHeight);
    cells =  playerZoneFactory.addOrderPlayerZones(cells);
  }
}