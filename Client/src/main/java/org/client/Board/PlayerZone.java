package org.client.Board;

import javafx.scene.paint.Color;

public class PlayerZone {

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
