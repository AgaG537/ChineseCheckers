package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Factory class for generating player zones on the board.
 */
public class PlayerZoneFactory {

  /**
   * Adds player zones to the board for a specified number of players.
   *
   * @param numPlayers The number of players.
   * @param boardWidth The width of the board.
   * @param boardHeight The height of the board.
   * @param playerZoneHeight The height of each player's zone.
   * @param cells The current state of the board's cells.
   * @return The updated board cells with player zones added.
   */
  public static Cell[][] addPlayerZones(int numPlayers, int boardWidth, int boardHeight, int playerZoneHeight, Cell[][] cells) {
    int[][] playerZonesStartPoints = {
        {0, (boardWidth / 2)}, // Upper zone [0]
        {playerZoneHeight * 2 - 1, boardWidth - playerZoneHeight},  // Right upper zone xxx [1]
        {boardHeight - playerZoneHeight * 2, boardWidth - playerZoneHeight}, // Right bottom zone [2]
        {boardHeight - 1, (boardWidth / 2)}, // Bottom zone xxx [3]
        {boardHeight - playerZoneHeight * 2, playerZoneHeight - 1}, // Left bottom zone [4]
        {playerZoneHeight * 2 - 1, playerZoneHeight - 1} // Left upper zone xxx [5]
    };

    int[] activeZoneNums = new int[6];
    for (int i = 0; i < activeZoneNums.length; i++) {
      activeZoneNums[0] = 0;
    }
    switch (numPlayers) {
      case 2: activeZoneNums[0] = activeZoneNums[3] = 1; break;
      case 3: activeZoneNums[0] = activeZoneNums[2] = activeZoneNums[4] = 1; break;
      case 4: activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[4] = activeZoneNums[5] = 1; break;
      default: activeZoneNums[0] = activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[3] = activeZoneNums[4] = activeZoneNums[5] = 1;
    }

    int defaultPlayerNum = 1;
    int playerNum = 0;

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i % 2 == 0) {
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            Color color = ColorManager.generateDefaultColor(i + 1);
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setCellZone(color, playerNum, cells[row][col]);
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            Color color = ColorManager.generateDefaultColor(i + 1);
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setCellZone(color, playerNum, cells[row][col]);
          }
          k++;
        }
      }

      if (playerNum == defaultPlayerNum) {
        defaultPlayerNum++;
      }

    }
    return cells;
  }

  /**
   * Configures a cell by setting its initial player number and color.
   * Creates a pawn for the player if their number is non-zero.
   *
   * @param color The color to set for the cell.
   * @param playerNum The player's number (0 if no player, otherwise the player's number).
   * @param currentCell The cell to update with the player's information.
   */
  public static void setCellZone(Color color, int playerNum, Cell currentCell) {
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneColor(color);
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum,color,currentCell);
    }
  }


}
