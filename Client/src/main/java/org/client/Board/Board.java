package org.client.Board;

/**
 * Represents the game board for Chinese Checkers.
 * The board is initialized with specific dimensions and divided into zones for players.
 */
public class Board {

  private final int playerZoneHeight;
  private final int boardHeight; //17
  private final int boardWidth; //25
  private final Cell[][] cells;

  /**
   * Constructor for the Board class. Initializes the board and player zones.
   */
  public Board(int marblesPerPlayer) {
    playerZoneHeight = countPlayerZoneHeight(marblesPerPlayer);
    boardHeight = playerZoneHeight * 4 + 1;
    boardWidth = (playerZoneHeight * 6) + 1;

    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();
    initializePlayerZones();
  }

  private int countPlayerZoneHeight(int marblesPerPlayer) {
    int sum = 0;
    int heightCounter = 0;
    while (sum < marblesPerPlayer) {
      heightCounter++;
      sum += heightCounter;
    }
    if (sum == marblesPerPlayer) {
      return heightCounter;
    }
    return 0;
  }

  /**
   * Initializes the board structure by marking valid positions inside the board.
   */
  private void initializeBoard() {
    int i, j, k;

    for (i = 0; i < boardHeight; i++) {
      for (j = 0; j < boardWidth; j++) {
        cells[i][j] = new Cell(i, j);
      }
    }

    k = 0;
    for (i = 0; i < boardHeight - playerZoneHeight; i++) {
      for (j = (boardWidth / 2) - k; j <= (boardWidth / 2) + k; j += 2) {
        if (!cells[i][j].isInsideBoard()) {
          cells[i][j].setInsideBoard();
        }
        if (!cells[boardHeight - 1 - i][j].isInsideBoard()) {
          cells[boardHeight - 1 - i][j].setInsideBoard();
        }
      }
      k++;
    }

  }

  /**
   * Assigns specific zones of the board to different players.
   */
  private void initializePlayerZones() {
    int[][] playerZonesStartPoints = {
        {0, (boardWidth / 2)}, // Upper zone
        {playerZoneHeight * 2 - 1, boardWidth - playerZoneHeight},  // Right upper zone xxx
        {boardHeight - playerZoneHeight * 2, boardWidth - playerZoneHeight}, // Right bottom zone
        {boardHeight - 1, (boardWidth / 2)}, // Bottom zone xxx
        {boardHeight - playerZoneHeight * 2, playerZoneHeight - 1}, // Left bottom zone
        {playerZoneHeight * 2 - 1, playerZoneHeight - 1} // Left upper zone xxx
    };

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i == 0 || i == 2 || i == 4) {
        for (int row = rowStart; row < rowStart + 4; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            cells[row][col].setUserNum(i + 1);
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - 4; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            cells[row][col].setUserNum(i + 1);
          }
          k++;
        }
      }

    }
  }




  public int getBoardWidth() {
    return boardWidth;
  }

  public int getBoardHeight() {
    return boardHeight;
  }

  public Cell getCell(int i, int j) {
    return cells[i][j];
  }

}
