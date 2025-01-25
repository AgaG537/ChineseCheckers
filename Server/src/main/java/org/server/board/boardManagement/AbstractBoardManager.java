package org.server.board.boardManagement;

import org.server.board.boardObjects.Cell;
import org.server.board.boardObjects.Pawn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class for managing board functionality, including board initialization,
 * player zones setup, pawn management, and gameplay validation.
 */
public abstract class AbstractBoardManager implements Board {
  protected final int playerZoneHeight;
  protected final int boardHeight; //17
  protected final int boardWidth; //25
  protected final int numOfPlayers;
  protected int[][] playerZonesStartPoints;
  protected int[][] playerZonesEdgePoints;
  protected int numOfCellsPerZone;
  protected int[] activeZoneNums;
  protected ArrayList<Integer> finishedPlayers;
  protected int seed;

  protected Cell[][] cells;

  /**
   * Constructor for initializing the board manager with the given game parameters.
   *
   * @param marblesPerPlayer The number of marbles each player has.
   * @param numOfPlayers The number of players in the game.
   * @param seed The seed value for randomization (if required).
   */
  public AbstractBoardManager(int marblesPerPlayer, int numOfPlayers, int seed) {
    playerZoneHeight = countPlayerZoneHeight(marblesPerPlayer);
    boardHeight = playerZoneHeight * 4 + 1;
    boardWidth = (playerZoneHeight * 6) + 1;
    this.numOfPlayers = numOfPlayers;
    this.seed = seed;

    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();
    initializeNeighbors();

    this.numOfCellsPerZone = calculateCellsPerZone(playerZoneHeight);
    this.finishedPlayers = new ArrayList<>();
    this.playerZonesStartPoints = initializeZoneStartPoints(boardWidth, boardHeight, playerZoneHeight);
    this.playerZonesEdgePoints = initializeZoneEdgePoints(boardWidth, boardHeight, playerZoneHeight);
    this.activeZoneNums = new int[6];
    markActiveZones();
  }

  /**
   * Initializes the start points for each player's zone on the board.
   *
   * @param boardWidth The width of the board.
   * @param boardHeight The height of the board.
   * @param playerZoneHeight The height of each player's zone.
   * @return A 2D array representing the starting coordinates for each player's zone.
   */
  public int[][] initializeZoneStartPoints(int boardWidth, int boardHeight, int playerZoneHeight) {
    return new int[][]{
        {0, (boardWidth / 2)},                            // Upper zone
        {playerZoneHeight * 2 - 1, boardWidth - playerZoneHeight}, // Right upper zone
        {boardHeight - playerZoneHeight * 2, boardWidth - playerZoneHeight}, // Right bottom zone
        {boardHeight - 1, (boardWidth / 2)},              // Bottom zone
        {boardHeight - playerZoneHeight * 2, playerZoneHeight - 1}, // Left bottom zone
        {playerZoneHeight * 2 - 1, playerZoneHeight - 1}  // Left upper zone
    };
  }

  public int[][] initializeZoneEdgePoints(int boardWidth, int boardHeight, int playerZoneHeight) {
    return new int[][]{
        {0, (boardWidth / 2)},                  // Upper zone
        {playerZoneHeight, boardWidth - 1},     // Right upper zone
        {boardHeight - playerZoneHeight - 1, boardWidth - 1}, // Right bottom zone
        {boardHeight - 1, (boardWidth / 2)},    // Bottom zone
        {boardHeight - playerZoneHeight - 1, 0},              // Left bottom zone
        {playerZoneHeight, 0}                   // Left upper zone
    };
  }

  /**
   * Calculates the number of cells that a player's zone occupies.
   *
   * @param playerZoneHeight The height of the player's zone.
   * @return The number of cells in the player's zone.
   */
  protected int calculateCellsPerZone(int playerZoneHeight) {
    int counter = playerZoneHeight;
    int numOfCells = 0;
    while (counter > 0) {
      numOfCells += counter;
      counter--;
    }
    return numOfCells;
  }

  /**
   * Marks the active zones based on the number of players.
   */
  protected void markActiveZones() {
    Arrays.fill(activeZoneNums, 0);
    switch (numOfPlayers) {
      case 2 -> activeZoneNums[0] = activeZoneNums[3] = 1;
      case 3 -> activeZoneNums[0] = activeZoneNums[2] = activeZoneNums[4] = 1;
      case 4 -> activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[4] = activeZoneNums[5] = 1;
      default -> Arrays.fill(activeZoneNums, 1);
    }
  }

  /**
   * Sets up player zones on the board by assigning cells to zones based on the number of players.
   * Initializes player numbers in active zones.
   */
  public void setupPlayerZones() {
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

  /**
   * Sets up the specified cell by assigning it to a zone and placing a pawn if necessary.
   * Depending on the active zone, the cell is either assigned to the default player or left empty.
   * If the cell belongs to an active zone, it is also assigned a pawn representing the player.
   *
   * @param cell The cell to be set up.
   * @param zoneNum The zone number where the cell belongs.
   * @param defaultPlayerNum The player number to be assigned to the cell if it is part of the active zone.
   * @param activeZoneNums An array indicating which zones are active (1 for active, 0 for inactive).
   */
  public abstract void setupCell(Cell cell, int zoneNum, int defaultPlayerNum, int[] activeZoneNums);

  /**
   * Assigns a cell to a specific player zone, setting its zone number and player number.
   *
   * @param cell       The cell to assign.
   * @param zoneNum    The number of the zone.
   * @param playerNum  The player number associated with the zone.
   */
  protected void assignCellToZone(Cell cell, int zoneNum, int playerNum) {
    cell.setZoneNum(zoneNum);
    cell.setInitialPlayerNum(playerNum);
  }

  /**
   * Assigns a pawn to a specific cell, setting its player number.
   *
   * @param cell       The cell to assign.
   * @param playerNum  The player number associated with the pawn.
   */
  protected void assignPawnToCell(Cell cell, int playerNum) {
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum, cell);
      cell.pawnMoveIn(pawn);
    }
  }

  /**
   * Verifies whether a player has completed their zone and won.
   *
   * @return The number of the winning player if a win condition is met, otherwise 0.
   */
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

  /**
   * Abstract method to get the target player for checking win condition based on the player zone.
   *
   * @param numOfPlayers The number of players.
   * @param zoneNum The zone number to check.
   * @return The player number for the target player.
   */
  protected abstract int getTargetPlayer(int numOfPlayers, int zoneNum);

  /**
   * Verifies whether the current row index is within the bounds of the player zone.
   *
   * @param i         The index of the player zone.
   * @param row       The current row index.
   * @param rowStart  The starting row index for the zone.
   * @return True if the row index is within the bounds of the zone; false otherwise.
   */
  private boolean checkRow(int i, int row, int rowStart) {
    if (i % 2 == 0) {
      return row < rowStart + playerZoneHeight;
    } else {
      return row > rowStart - playerZoneHeight;
    }
  }

  /**
   * Advances the row index based on the direction of the zone.
   *
   * @param i   The index of the player zone.
   * @param row The current row index.
   * @return The updated row index.
   */
  private int advanceRow(int i, int row) {
    if (i % 2 == 0) {
      return row + 1;
    } else {
      return row - 1;
    }
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
    return row >= 0 && row < boardHeight
        && col >= 0 && col < boardWidth
        && cells[row][col].isInsideBoard();
  }

  /**
   * Retrieves the width of the board.
   *
   * @return Board width.
   */
  public int getBoardWidth() {
    return boardWidth;
  }

  /**
   * Retrieves the height of the board.
   *
   * @return Board height.
   */
  public int getBoardHeight() {
    return boardHeight;
  }

  /**
   * Retrieves a specific cell from the board.
   *
   * @param i Row index.
   * @param j Column index.
   * @return The cell at the specified position.
   */
  public Cell getCell(int i, int j) {
    System.out.printf("rows: %d, cols: %d\n", cells.length, cells[0].length);
    return cells[i][j];
  }

  /**
   * Creates a string for pawn creation at a specified position.
   *
   * @param row Row index.
   * @param col Column index.
   * @return String representation of pawn creation.
   */
  @Override
  public String makeCreate(int row, int col) {
    if (cells[row][col].getPawn() == null) {
      return "[CREATE] " + row + " " + col + " " + 0 + " " + numOfPlayers;
    } else {
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

  public int[] getActiveZoneNums() {
    return activeZoneNums;
  }

  @Override
  public void removePawns() {
    for (Cell[] cellRow : cells) {
      for (Cell cell : cellRow) {
        cell.pawnMoveOut();
      }
    }
  }

  @Override
  public void setCells(Cell[][] cells) {
    this.cells = cells;
  }

  @Override
  public abstract int[] getDestinationPoint(int playerNum);

  @Override
  public abstract int getDestinationZoneNum(int playerNum);
}

