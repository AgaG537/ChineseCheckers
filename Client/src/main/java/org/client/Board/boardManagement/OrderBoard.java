package org.client.Board.boardManagement;

import javafx.scene.paint.Color;
import org.client.Board.boardObjects.Cell;

/**
 * Represents an Order Out of Chaos game board with specific player zone
 * setup for Order Out of Chaos variant rules.
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
   * Assigns a pawn to a cell in the OrderBoard variant.
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