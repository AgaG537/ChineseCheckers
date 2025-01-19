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

  /**
   * Assigns a pawn to a cell in the YinYang variant.
   * Configures the cell with the pawn's properties and sets the zone color.
   *
   * @param playerNum The number of the player owning the pawn.
   * @param color     The color associated with the pawn.
   * @param cell      The cell where the pawn is to be placed.
   */
  @Override
  public void setupPawn(int playerNum, Color color, Cell cell) {
    if (playerNum != 0) {
      assignPawnToCell(cell, playerNum, color);
    }
    cell.setZoneColor(color);
  }
}