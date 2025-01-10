package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Adds player zones to the board.
 */
public class PlayerZone {

  /**
   * Adds a player zone to the board.
   *
   * @param rowStart The starting row of the player zone.
   * @param colStart The starting column of the player zone.
   * @param color The color representing the player.
   * @param playerZoneHeight The height of the player zone.
   * @param playerNum The player's number.
   * @param cells The current state of the board's cells.
   * @param asc Whether to add the zone in ascending or descending order.
   * @return The updated board cells.
   */
  public static Cell[][] addPlayerZone(int rowStart, int colStart, Color color, int playerZoneHeight, int playerNum, Cell[][] cells, boolean asc) {
    int k = 0;
    if (asc) {
      for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
        for (int col = colStart - k; col <= colStart + k; col += 2) {
          cells[row][col].setInitialPlayerNum(playerNum);
          Pawn pawn = new Pawn(playerNum,color,cells[row][col]);
        }
        k++;
      }
    }
    else {
      for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
        for (int col = colStart - k; col <= colStart + k; col += 2) {
          cells[row][col].setInitialPlayerNum(playerNum);
          Pawn pawn = new Pawn(playerNum,color,cells[row][col]);
        }
        k++;
      }
    }
    return cells;
  }
}
