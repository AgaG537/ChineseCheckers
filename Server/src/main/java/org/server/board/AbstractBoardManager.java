package org.server.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class representing the common functionality for managing different board types.
 * Provides the structure for initializing the board, managing moves, and validating gameplay.
 */
public abstract class AbstractBoardManager implements Board {
  protected final int playerZoneHeight;
  protected final int boardHeight; //17
  protected final int boardWidth; //25
  protected final int numOfPlayers;
  protected int[][] playerZonesStartPoints;
  protected int numOfCellsPerZone;
  protected int[] activeZoneNums;
  protected ArrayList<Integer> finishedPlayers;
  protected int seed;

  protected Cell[][] cells;

  protected final int constraintSize;

  /**
   * Constructor for the BoardManager class.
   *
   * @param marblesPerPlayer The number of marbles per player.
   * @param numOfPlayers     The number of players in the game.
   */
  public AbstractBoardManager(int marblesPerPlayer, int numOfPlayers, int seed) {
    playerZoneHeight = countPlayerZoneHeight(marblesPerPlayer);
    boardHeight = playerZoneHeight * 4 + 1;
    boardWidth = (playerZoneHeight * 6) + 1;
    constraintSize = (1000 / boardWidth) / 2;
    this.numOfPlayers = numOfPlayers;
    this.seed = seed;

    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();
    initializeNeighbors();

    this.numOfCellsPerZone = calculateCellsPerZone(playerZoneHeight);
    this.finishedPlayers = new ArrayList<>();
    this.playerZonesStartPoints = initializeZoneStartPoints(boardWidth, boardHeight, playerZoneHeight);
    this.activeZoneNums = new int[6];
    markActiveZones();
  }

  protected int[][] initializeZoneStartPoints(int boardWidth, int boardHeight, int playerZoneHeight) {
    return new int[][]{
        {0, (boardWidth / 2)},                            // Upper zone
        {playerZoneHeight * 2 - 1, boardWidth - playerZoneHeight}, // Right upper zone
        {boardHeight - playerZoneHeight * 2, boardWidth - playerZoneHeight}, // Right bottom zone
        {boardHeight - 1, (boardWidth / 2)},              // Bottom zone
        {boardHeight - playerZoneHeight * 2, playerZoneHeight - 1}, // Left bottom zone
        {playerZoneHeight * 2 - 1, playerZoneHeight - 1}  // Left upper zone
    };
  }

  protected int calculateCellsPerZone(int playerZoneHeight) {
    int counter = playerZoneHeight;
    int numOfCells = 0;
    while (counter > 0) {
      numOfCells += counter;
      counter--;
    }
    return numOfCells;
  }

  protected void markActiveZones() {
    Arrays.fill(activeZoneNums, 0);
    switch (numOfPlayers) {
      case 2 -> activeZoneNums[0] = activeZoneNums[3] = 1;
      case 3 -> activeZoneNums[0] = activeZoneNums[2] = activeZoneNums[4] = 1;
      case 4 -> activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[4] = activeZoneNums[5] = 1;
      default -> {
        Arrays.fill(activeZoneNums, 1);
      }
    }
  }

  protected void setupPlayerZones() {
    int defaultPlayerNum = 1;

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];
      int k = 0;

      for (int row = rowStart; checkRow(i, row, rowStart); row = advanceRow(i, row)) {
        for (int col = colStart - k; col <= colStart + k; col += 2) {
          setupCell(cells[row][col], i + 1, defaultPlayerNum, activeZoneNums);
        }
        k++;
      }

      if (activeZoneNums[i] == 1) {
        defaultPlayerNum++;
      }
    }
  }

  protected abstract void setupCell(Cell cell, int zoneNum, int playerNum, int[] activeZoneNums);

  protected void assignCellToZone(Cell cell, int zoneNum, int playerNum) {
    cell.setZoneNum(zoneNum);
    cell.setInitialPlayerNum(playerNum);
  }

  protected void assignPawnToCell(Cell cell, int playerNum) {
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum, cell);
      cell.pawnMoveIn(pawn);
    }
  }

  public int checkWinCondition() {
    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int targetPlayer = getTargetPlayer(numOfPlayers, i + 1);
      if (!finishedPlayers.contains(targetPlayer)) {
        int[] zoneStartPoint = playerZonesStartPoints[i];
        int rowStart = zoneStartPoint[0];
        int colStart = zoneStartPoint[1];
        int k = 0;
        int playerPawnCount = 0;

        for (int row = rowStart; checkRow(i, row, rowStart); row = advanceRow(i, row)) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            if (cells[row][col].getPawn() != null) {
              if (cells[row][col].getPawn().getPlayerNum() == targetPlayer) {
                playerPawnCount++;
              }
            }
          }
          k++;
        }

        if (playerPawnCount == numOfCellsPerZone) {
          finishedPlayers.add(targetPlayer);
          return targetPlayer;
        }
      }
    }
    return 0;
  }

  protected abstract int getTargetPlayer(int numOfPlayers, int zoneNum);

  private boolean checkRow(int i, int row, int rowStart) {
    if (i % 2 == 0) {
      return row < rowStart + playerZoneHeight;
    } else {
      return row > rowStart - playerZoneHeight;
    }
  }

  private int advanceRow(int i, int row) {
    if (i % 2 == 0) {
      return row + 1;
    } else {
      return row - 1;
    }
  }

  /**
   * @return The size constraint used for cell rendering in the GUI.
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
        if (!current.isInsideBoard()) {
          continue; // Skip cells not inside the board.
        }

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
    System.out.printf("rows: %d, cols: %d\n", cells.length, cells[0].length);
    return cells[i][j];
  }

  @Override
  public String makeCreate(int row, int col) {
    if (cells[row][col].getPawn() == null) {
      return "[CREATE] " + row + " " + col + " " + 0 + " " + numOfPlayers;
    }
    else {
      return "[CREATE] " + row + " " + col + " " + cells[row][col].getPawn().getPlayerNum() + " " + numOfPlayers;
    }
  }

  /**
   * Retrieves the 2D array of cells representing the board.
   *
   * @return A 2D array of Cell objects.
   */
  @Override
  public Cell[][] getCells() {
    return cells;
  }

}

