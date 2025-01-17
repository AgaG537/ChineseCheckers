package org.client.Board;

import javafx.scene.paint.Color;

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

  @Override
  protected void setupPawn(int playerNum, Color color, Cell cell) {
    assignPawnToCell(cell, playerNum, color);
  }
}