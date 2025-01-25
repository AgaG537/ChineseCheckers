package org.client.Board;

import javafx.scene.paint.Color;

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
   * Assigns a pawn to a cell in the StandardBoard variant.
   * Configures the cell with the pawn's properties.
   *
   * @param playerNum The number of the player owning the pawn.
   * @param color     The color associated with the pawn.
   * @param cell      The cell where the pawn is to be placed.
   */
  @Override
  public void setupPawn(int playerNum, Color color, Cell cell) {
    assignPawnToCell(cell, playerNum, color);
  }
}
