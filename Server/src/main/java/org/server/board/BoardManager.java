package org.server.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardManager implements Board {

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
  public BoardManager(int marblesPerPlayer, int numOfPlayers, String variant) {
    playerZoneHeight = countPlayerZoneHeight(marblesPerPlayer);
    boardHeight = playerZoneHeight * 4 + 1;
    boardWidth = (playerZoneHeight * 6) + 1;
    constraintSize = (1000 / boardWidth) / 2;

    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();
    initializeNeighbors();

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
        }
        if (!cells[boardHeight - 1 - i][j].isInsideBoard()) {
          cells[boardHeight - 1 - i][j].setInsideBoard();
        }
      }
      k++;
    }
  }


  /**
   * Adds neighbors to each cell in the cells grid.
   * Considers the hexagonal layout of the board.
   */
  private void initializeNeighbors() {
    int[][] directions = {
        {-1, -1}, {-1, 1}, // Top-left, Top-right
        {0, -2}, {0, 2},   // Left, Right
        {1, -1}, {1, 1}    // Bottom-left, Bottom-right
    };

    for (int i = 0; i < boardHeight; i++) {
      for (int j = 0; j < boardWidth; j++) {
        Cell current = cells[i][j];
        if (!current.isInsideBoard()) continue; // Skip cells not inside the board.

        List<Cell> neighbors = new ArrayList<>();
        for (int[] dir : directions) {
          int ni = i + dir[0];
          int nj = j + dir[1];
          if (isValidCell(ni, nj)) {
            neighbors.add(cells[ni][nj]);
          }
        }
        current.setNeighbors(neighbors);
      }
    }
  }

  /**
   * Checks if the given cell coordinates are valid and inside the board.
   *
   * @param row The row index.
   * @param col The column index.
   * @return True if the cell is valid and inside the board; false otherwise.
   */
  private boolean isValidCell(int row, int col) {
    return row >= 0 && row < boardHeight &&
        col >= 0 && col < boardWidth &&
        cells[row][col].isInsideBoard();
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

  @Override
  public String makeMove(int userId, String input) {
    return input;
  }

  @Override
  public boolean validateMove(int userNum, String input) {
    int[] vals = decodeInput(input);

    int rowStart = vals[0];
    int colStart = vals[1];
    int playerStart = vals[2];
    int rowEnd = vals[3];
    int colEnd = vals[4];
    int playerEnd = vals[5];

    int[][] directions = {
        {-1, -1}, //upper left
        {0, -2}, // left
        {1,-1}, // bottom left
        {-1,-1}, // bottom right
        {0,2}, // right
        {-1,1} // upper right
    };

    Pawn pawn = cells[rowStart][colStart].getPawn();
    ArrayList<int[]> visited = new ArrayList<>();

    // Add verification if pawn exists

    //Move by one to empty space
    for (int[] direction : directions) {
      int tmpRow = rowStart + direction[0];
      int tmpCol = colStart + direction[1];
      if (tmpRow == rowEnd && tmpCol == colEnd) {
        if (cells[tmpRow][tmpCol].isOccupied()) {
          return false;
        }
        return true;
      }
    }

    // Jumps
    for (int[] direction : directions) {
      int tmpRow = rowStart + direction[0];
      int tmpCol = colStart + direction[1];
      for (Cell neighbor : cells[rowStart][colStart].getNeighbors()) {
        tmpRow = tmpRow + direction[0];
        tmpCol = tmpCol + direction[1];
        if (0 <= tmpRow && tmpRow < cells.length && 0 <= tmpCol && tmpCol < cells[0].length) {
          if (cells[tmpRow][tmpCol].isOccupied()) {
            return false;
          }
          if (tmpRow == rowEnd && tmpCol == colEnd) {
            return true;
          }
          else {
            visited.add(new int[]{rowStart,colStart});
            if (validateMoveRecursively(tmpRow,tmpCol,rowEnd,colEnd,visited)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  public boolean validateMoveRecursively(int startRow, int startCol, int endRow, int endCol, ArrayList<int[]> visited) {
    if (cells[startRow][startCol].isOccupied()) {
      return false;
    }
    if (startRow == endRow && startCol == endCol) {
      return true;
    }
    if (visited.stream().anyMatch(arr -> Arrays.equals(arr, new int[]{startRow, startCol}))) {
      return false;
    }

    visited.add(new int[]{startRow, startCol});

    int[][] directions = {
        {-1, -1}, //upper left
        {0, -2}, // left
        {1,-1}, // bottom left
        {-1,-1}, // bottom right
        {0,2}, // right
        {-1,1} // upper right
    };

    for (int[] direction : directions) {
      int tmpRow = startRow + direction[0];
      int tmpCol = startCol + direction[1];
      if (0 <= tmpRow && tmpRow < cells.length && 0 <= tmpCol && tmpCol < cells[0].length) {
        if (cells[tmpRow][tmpCol].isOccupied()) {
          int jumpRow = tmpRow + direction[0];
          int jumpCol = tmpCol + direction[1];

          if (0 <= jumpRow && jumpRow < cells.length && 0 <= jumpCol && jumpCol < cells[0].length) {
            if (!cells[jumpRow][jumpCol].isOccupied()) {
              if (validateMoveRecursively(jumpRow,jumpCol,endRow,endCol,visited)) {
                return true;
              }
            }
          }
          }
      }
    }
    return false;
  }

  private int[] decodeInput(String input) {
    String[] tokens = input.split(" ");
    int[] result = new int[tokens.length];
    try {
      for (int i = 0; i < tokens.length; i++) {
        result[i] = Integer.parseInt(tokens[i]);
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return result;
  }


}
