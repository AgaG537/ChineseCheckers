package org.client.Board;

import org.client.Client;

/**
 * Represents a YinYang game board with a specialized two-player zone setup.
 */
public class YinYangBoard extends AbstractBoard {
  /**
   * Constructs a YinYangBoard with the specified number of marbles per player.
   * Note: The YinYang variant is restricted to two players.
   *
   * @param marblesPerPlayer Number of marbles allocated to each player.
   */
  public YinYangBoard(int marblesPerPlayer) {
    super(marblesPerPlayer, 0);
  }

  /**
   * Sets up the player zones for the YinYang game variant.
   * Player zones are tailored for two players.
   *
   * @param numOfPlayers Number of players (ignored, as this variant always uses two players).
   */
  @Override
  protected void setupPlayerZones(int numOfPlayers) {
    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers, boardWidth, boardHeight, playerZoneHeight);
    cells =  playerZoneFactory.setEmptyZones(numOfPlayers, boardWidth, boardHeight, playerZoneHeight, cells);
  }
}