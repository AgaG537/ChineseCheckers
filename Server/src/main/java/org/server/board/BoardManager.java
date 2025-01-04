package org.server.board;


/**
 * Represents the game board for Chinese Checkers.
 * The board is initialized with specific dimensions and divided into zones for players.
 */
public class BoardManager implements Board {

  private static final int boardHeight = 17;
  private static final int boardWidth = 25;
  private static final int playerZoneHeight = (boardHeight - 1) / 4;
  private static final int boardWidthCenter = (boardWidth) / 2;
  private final Cell[][] cells;

  /**
   * Constructor for the Board class. Initializes the board and player zones.
   */
  public BoardManager() {
    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();
    initializePlayerZones();
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
      for (j = boardWidthCenter - k; j <= boardWidthCenter + k; j += 2) {
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
        {0, boardWidthCenter}, // Upper zone
        {boardHeight - playerZoneHeight * 2, playerZoneHeight - 1}, // Left bottom zone
        {boardHeight - playerZoneHeight * 2, boardWidth - playerZoneHeight}, // Right bottom zone
        {boardHeight - 1, boardWidthCenter}, // Bottom zone
        {playerZoneHeight * 2 - 1, playerZoneHeight - 1}, // Left upper zone
        {playerZoneHeight * 2 - 1, boardWidth - playerZoneHeight}  // Right upper zone
    };

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i == 0 || i == 1 || i == 2) {
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

  /**
   * Prints the board to the console for debugging purposes.
   */
  public void printBoard() {
    for (int i = 0; i < boardHeight; i++) {
      StringBuilder s = new StringBuilder();
      for (int j = 0; j < boardWidth; j++) {
        if (cells[i][j].isInsideBoard()) {
          s.append(cells[i][j].getUserNum());
        } else {
          s.append(".");
        }
      }
      System.out.println(s);
    }
  }

  @Override
  public String makeMove(int userId, String input) {
    return input;
  }

  /**
   * Main method to create and display the board.
   */
  public static void main(String[] args) {
    BoardManager board = new BoardManager();
    board.printBoard();
    // for now just displaying board in terminal
  }
}
