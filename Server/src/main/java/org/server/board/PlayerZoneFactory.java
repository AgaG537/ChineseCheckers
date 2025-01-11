package org.server.board;


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

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i % 2 == 0) {
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            int playerNum;
            if (activeZoneNums[i] == 1) {
              playerNum = i + 1;
            } else {
              playerNum = 0;
            }
            setCellZone(playerNum, cells[row][col]);
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            int playerNum;
            if (activeZoneNums[i] == 1) {
              playerNum = i + 1;
            } else {
              playerNum = 0;
            }
            setCellZone(playerNum, cells[row][col]);
          }
          k++;
        }
      }

    }

//    switch (numPlayers) {
//      case 2:
//        // player num 1 upper zone
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[0][0],playerZonesStartPoints[0][1], generateDefaultColor(1),playerZoneHeight,1,cells,isAsc(1));
//        //player num 4 bottom zone
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[3][0],playerZonesStartPoints[3][1], generateDefaultColor(4),playerZoneHeight,4,cells,isAsc(4));
//      break;
//      case 3:
//        // player num 1 upper zone
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[0][0],playerZonesStartPoints[0][1], generateDefaultColor(1),playerZoneHeight,1,cells,isAsc(1));
//        // player num 3 right bottom
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[2][0],playerZonesStartPoints[2][1], generateDefaultColor(3),playerZoneHeight,3,cells,isAsc(3));
//        // player num 5 left bottom
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[4][0],playerZonesStartPoints[4][1], generateDefaultColor(4),playerZoneHeight,4,cells,isAsc(4));
//      break;
//      case 4:
//        // player num 2 right upper
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[1][0],playerZonesStartPoints[1][1], generateDefaultColor(2),playerZoneHeight,2,cells,isAsc(2));
//        // player num 3 right bottom
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[2][0],playerZonesStartPoints[2][1], generateDefaultColor(3),playerZoneHeight,3,cells,isAsc(3));
//        // player num 5 left bottom
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[4][0],playerZonesStartPoints[4][1], generateDefaultColor(5),playerZoneHeight,5,cells,isAsc(5));
//        // player num 6 left upper
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[5][0],playerZonesStartPoints[5][1], generateDefaultColor(6),playerZoneHeight,6,cells,isAsc(6));
//      break;
//      case 6:
//        // player num 1 upper zone
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[0][0],playerZonesStartPoints[0][1], generateDefaultColor(1),playerZoneHeight,1,cells,isAsc(1));
//        // player num 2 right upper
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[1][0],playerZonesStartPoints[1][1], generateDefaultColor(2),playerZoneHeight,2,cells,isAsc(2));
//        // player num 3 right bottom
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[2][0],playerZonesStartPoints[2][1], generateDefaultColor(3),playerZoneHeight,3,cells,isAsc(3));
//        //player num 4 bottom zone
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[3][0],playerZonesStartPoints[3][1], generateDefaultColor(4),playerZoneHeight,4,cells,isAsc(4));
//        // player num 5 left bottom
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[4][0],playerZonesStartPoints[4][1], generateDefaultColor(5),playerZoneHeight,5,cells,isAsc(5));
//        // player num 6 left upper
//        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[5][0],playerZonesStartPoints[5][1], generateDefaultColor(6),playerZoneHeight,6,cells,isAsc(6));
//        break;
//    }
    return cells;
  }

  /**
   * Sets the initial player number and color for a given cell, and places a pawn if the player number is non-zero.
   *
   * @param playerNum The player's number (0 if no player, otherwise the player's number).
   * @param currentCell The cell to update with the player's information.
   */
  public static void setCellZone(int playerNum, Cell currentCell) {
    currentCell.setInitialPlayerNum(playerNum);
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum,currentCell);
    }
  }


}
