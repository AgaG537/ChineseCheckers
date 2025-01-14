package org.client.Board;

import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Factory class for generating player zones on the board.
 */
public class PlayerZoneFactory {

  /**
   * Adds player zones to the board for a specified number of players.
   *
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
   * Adds player zones to the board for a specified number of players.
   *
   * @param numPlayers The number of players.
   * @param boardWidth The width of the board.
   * @param boardHeight The height of the board.
   * @param playerZoneHeight The height of each player's zone.
   * @param cells The current state of the board's cells.
   * @return The updated board cells with player zones added.
   */
  public static Cell[][] addOrderPlayerZones(int numPlayers, int boardWidth, int boardHeight, int playerZoneHeight, Cell[][] cells) {
    int marbles = (1+playerZoneHeight)*playerZoneHeight/2;
    System.out.printf("PZH %d, marbles: %d",playerZoneHeight, marbles);


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
            setOrderCellZone(color, playerNum, cells[row][col]);
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
            setOrderCellZone(color, playerNum, cells[row][col]);
          }
          k++;
        }
      }

      if (playerNum == defaultPlayerNum) {
        defaultPlayerNum++;
      }
    }

    // Randomly distribute pawns in the middle of the board.
    Random random = new Random(1234);
    int player = 0;
    int currPlayer = 0;
    for (int j : activeZoneNums) {
      player++;
      if (j!=0) {
        currPlayer++;
        System.out.println("Currplayer: " + currPlayer);
        int i = 0;
        while (i < marbles) {
          System.out.printf("i: %d, marbles: %d\n", i, marbles);
          int row = random.nextInt(boardHeight);
          int col = random.nextInt(boardWidth);
          System.out.println(row + " " + col);

          if (cells[row][col].getPawn() == null && cells[row][col].getZoneColor().toString().equals("0xffa64dff")) {
            Color pawnColor = ColorManager.generateDefaultColor(player);
            Pawn pawn = new Pawn(currPlayer, pawnColor, cells[row][col]);
            cells[row][col].pawnMoveIn(pawn);
            System.out.printf("row: %d, col: %d\n", row, col);
            System.out.println(cells[row][col].getPawn());
            i++;
          }
        }
      }
    }

    return cells;
  }



  public static Cell[][] addYinYangZones(int boardWidth, int boardHeight, int playerZoneHeight, Cell[][] cells) {
    int marbles = (1+playerZoneHeight)*playerZoneHeight/2;
    Random random = new Random(1234);
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
      activeZoneNums[i] = 0;
    }

    int player1num = random.nextInt(6);
    int player2num = random.nextInt(6);
    while (player2num == player1num) {
      player2num = random.nextInt(6);
    }

    activeZoneNums[player2num] = activeZoneNums[player1num]= 1;
    Color[] colors = {Color.BLACK, Color.WHITE};
    int defaultPlayerNum = 1;
    int playerNum = 0;

    int currColor = 0;
    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      int counter = 0;
      if (i % 2 == 0) {
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            Color color = ColorManager.generateDefaultColor(i + 1);
            Color pawnColor = Color.TRANSPARENT;
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setYinYangCellZone(color, playerNum, cells[row][col]);
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            Color color = ColorManager.generateDefaultColor(i + 1);
            Color pawnColor = Color.TRANSPARENT;
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setYinYangCellZone(color, playerNum, cells[row][col]);
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
    currentCell.setFlag(5);
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneColor(color);
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum,color,currentCell);
      currentCell.pawnMoveIn(pawn);
    }
  }


  public static void setYinYangCellZone(Color zoneColor, int playerNum, Cell currentCell) {
    currentCell.setFlag(5);
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneColor(zoneColor);
    Color pawnColor;
    if (playerNum != 0) {
      if (playerNum == 1) {
        pawnColor = Color.BLACK;
      }
      else {
        pawnColor = Color.WHITE;
      }
      Pawn pawn = new Pawn(playerNum,pawnColor,currentCell);
      currentCell.pawnMoveIn(pawn);
    }
  }


  private static void setOrderCellZone(Color color, int playerNum, Cell currentCell) {
    currentCell.setFlag(5);
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneColor(color);
  }
}
