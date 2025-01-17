package org.client.Board;

import javafx.scene.paint.Color;

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

  @Override
  protected void setupPawn(int playerNum, Color color, Cell cell) {
    if (playerNum != 0) {
      assignPawnToCell(cell, playerNum, color);
    }
    cell.setZoneColor(color);
  }
}