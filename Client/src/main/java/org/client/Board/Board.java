package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Represents the game board for Chinese Checkers.
 * The board is initialized with specific dimensions
 * and divided into zones for players.
 */
public class Board {

  private final int playerZoneHeight;
  private final int boardHeight; //17
  private final int boardWidth; //25
  private Cell[][] cells;

  private final int constraintSize;

  /**
   * Constructor for the Board class.
   *
   * @param marblesPerPlayer The number of marbles per player.
   */
  public Board(int marblesPerPlayer, int numOfPlayers, String variant) {
    playerZoneHeight = countPlayerZoneHeight(marblesPerPlayer);
    boardHeight = playerZoneHeight * 4 + 1;
    boardWidth = (playerZoneHeight * 6) + 1;
    constraintSize = (1000 / boardWidth) / 2;

    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();

    cells = PlayerZoneFactory.addPlayerZones(numOfPlayers,boardWidth,boardHeight,playerZoneHeight,cells);
  }

  /**
   * @return The size constraint used
   * for cell rendering in the GUI.
   */
  public int getConstraintSize() {
    return constraintSize;
  }

  /**
   * Calculates the height of a player's triangular zone.
   *
   * @param marblesPerPlayer Number of marbles a player has.
   * @return The height of the triangular player zone.
   */
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
   * Initializes the board structure by marking
   * valid positions inside the board.
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
          cells[i][j].setInitialColor(Color.web("#ffa64d"));
        }
        if (!cells[boardHeight - 1 - i][j].isInsideBoard()) {
          cells[boardHeight - 1 - i][j].setInsideBoard();
          cells[boardHeight - 1 - i][j].setInitialColor(Color.web("#ffa64d"));
        }
      }
      k++;
    }

  }

  /**
   * Not used anymore, replaced by PlayerZoneFactory
   *
   * Assigns specific zones of the board to different
   * players and initializes their pawns.
   */
  private void initializePlayerZonesAndPawns() {
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
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            int playerNum = i + 1;
            cells[row][col].setInitialPlayerNum(playerNum);
            //cells[row][col].setCurrentPlayerNum(playerNum);
            Color cellColor;
            Color pawnColor;
            switch (playerNum) {
              case 1: cellColor = pawnColor = Color.RED; break;
              case 3: cellColor = pawnColor = Color.BLUE; break;
              default: cellColor = pawnColor = Color.WHITE;
            }
            Pawn pawn = new Pawn(playerNum,pawnColor,cells[row][col]);
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            int playerNum = i + 1;
            cells[row][col].setInitialPlayerNum(playerNum);
            //cells[row][col].setCurrentPlayerNum(playerNum);
            Color cellColor;
            Color pawnColor;
            switch (playerNum) {
              case 2: cellColor = pawnColor = Color.BLACK; break;
              case 4: cellColor = pawnColor = Color.GREEN; break;
              default: cellColor = pawnColor = Color.YELLOW;
            }
            Pawn pawn = new Pawn(playerNum,pawnColor,cells[row][col]);
          }
          k++;
        }
      }
    }
  }

  /**
   * @return The width of the board.
   */
  public int getBoardWidth() {
    return boardWidth;
  }

  /**
   * @return The height of the board.
   */
  public int getBoardHeight() {
    return boardHeight;
  }

  /**
   * Retrieves a specific cell from the board.
   *
   * @param i Row index of the cell.
   * @param j Column index of the cell.
   * @return The cell at the specified position.
   */
  public Cell getCell(int i, int j) {
    return cells[i][j];
  }

}
