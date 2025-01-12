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
  public Board(int marblesPerPlayer, int playerNum, int numOfPlayers, String variant) {
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
          cells[i][j].setZoneColor(Color.web("#ffa64d"));
        }
        if (!cells[boardHeight - 1 - i][j].isInsideBoard()) {
          cells[boardHeight - 1 - i][j].setInsideBoard();
          cells[boardHeight - 1 - i][j].setZoneColor(Color.web("#ffa64d"));
        }
      }
      k++;
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

  public void handleCommand(String command) {
    int[] vals = decodeInput(command);

    int rowStart = vals[0];
    int colStart = vals[1];
    int rowEnd = vals[2];
    int colEnd = vals[3];

    Pawn pawn = cells[rowStart][colStart].getPawn();
    cells[rowStart][colStart].pawnMoveOut();
    cells[rowEnd][colEnd].pawnMoveIn(pawn);
  }

  private int[] decodeInput(String input) {
    String[] tokens = input.split(" ");
    int[] result = new int[tokens.length];
    try {
      for (int i = 1; i < tokens.length; i++) {
        result[i] = Integer.parseInt(tokens[i]);
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return result;
  }
}
