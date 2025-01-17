package org.client.Board;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract class representing the common functionality for managing different board types.
 * Provides the structure for initializing the board.
 */
public abstract class AbstractBoard implements Board {
  protected final int playerZoneHeight;
  protected final int boardHeight;
  protected final int boardWidth;

  protected int numOfPlayers;
  protected int[][] playerZonesStartPoints;
  protected int numOfCellsPerZone;
  protected int[] activeZoneNums;
  protected ArrayList<Integer> finishedPlayers;
  protected int seed;

  protected final int constraintSize;
  protected Cell[][] cells;


  /**
   * Constructs an AbstractBoard with the specified number of marbles
   * per player and number of players.
   *
   * @param marblesPerPlayer The number of marbles each player has.
   * @param numOfPlayers The number of players in the game.
   */
  public AbstractBoard(int marblesPerPlayer, int numOfPlayers) {
    playerZoneHeight = calculatePlayerZoneHeight(marblesPerPlayer);
    boardHeight = playerZoneHeight * 4 + 1;
    boardWidth = playerZoneHeight * 6 + 1;
    constraintSize = (1000 / boardWidth) / 2;
    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();


    this.numOfCellsPerZone = calculateCellsPerZone(playerZoneHeight);
    this.finishedPlayers = new ArrayList<>();
    this.playerZonesStartPoints = initializeZoneStartPoints(boardWidth, boardHeight, playerZoneHeight);
    this.activeZoneNums = new int[6];
    markActiveZones();
    setupPlayerZones();
  }


  /**
   * Initializes the game board by creating and marking valid cells.
   */
  private void initializeBoard() {
    for (int i = 0; i < boardHeight; i++) {
      for (int j = 0; j < boardWidth; j++) {
        cells[i][j] = new Cell(i, j);
      }
    }
    markValidBoardPositions();
  }

  /**
   * Marks valid positions on the board based on its geometry.
   */
  private void markValidBoardPositions() {
    int k = 0;
    for (int i = 0; i < boardHeight - playerZoneHeight; i++) {
      for (int j = (boardWidth / 2) - k; j <= (boardWidth / 2) + k; j += 2) {
        setCellAsInsideBoard(i, j);
        setCellAsInsideBoard(boardHeight - 1 - i, j);
      }
      k++;
    }
  }


  /**
   * Sets a cell as part of the playable area and assigns a default color.
   *
   * @param row The row index of the cell.
   * @param col The column index of the cell.
   */
  private void setCellAsInsideBoard(int row, int col) {
    if (!cells[row][col].isInsideBoard()) {
      cells[row][col].setInsideBoard();
      cells[row][col].setZoneColor(Color.web("#ffa64d"));
    }
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
      default -> Arrays.fill(activeZoneNums, 1);
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
          Color color = ColorManager.generateDefaultColor(i + 1);
          int playerNum;
          if (activeZoneNums[i] == 1) {
            playerNum = defaultPlayerNum;
          } else {
            playerNum = 0;
          }
          assignCellToZone(cells[row][col], color, playerNum);
        }
        k++;
      }

      if (activeZoneNums[i] == 1) {
        defaultPlayerNum++;
      }
    }
  }

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

  protected void assignCellToZone(Cell cell, Color color, int playerNum) {
    cell.setFlag(5);
    cell.setInitialPlayerNum(playerNum);
    cell.setZoneColor(color);
  }

  protected void assignPawnToCell(Cell cell, int playerNum, Color color) {
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum, color, cell);
      cell.pawnMoveIn(pawn);
    }
  }

  /**
   * Calculates the height of a player's zone based on the number of marbles.
   *
   * @param marblesPerPlayer The number of marbles per player.
   * @return The height of the player zone, or 0 if invalid.
   */
  public int calculatePlayerZoneHeight(int marblesPerPlayer) {
    int sum = 0;
    int heightCounter = 0;
    while (sum < marblesPerPlayer) {
      heightCounter++;
      sum += heightCounter;
    }
    return sum == marblesPerPlayer ? heightCounter : 0;
  }

  /**
   * @return The size constraint used
   * for cell rendering in the GUI.
   */
  @Override
  public int getConstraintSize() {
    return constraintSize;
  }

  /**
   * @return The width of the board.
   */
  @Override
  public int getBoardWidth() {
    return boardWidth;
  }

  /**
   * @return The height of the board.
   */
  @Override
  public int getBoardHeight() {
    return boardHeight;
  }

  /**
   * Retrieves a specific cell from the board.
   *
   * @param row Row index of the cell.
   * @param col Column index of the cell.
   * @return The cell at the specified position.
   */
  @Override
  public Cell getCell(int row, int col) {
    return cells[row][col];
  }

  /**
   * Processes a command to move a pawn from one cell to another.
   *
   * @param command The command string specifying the move.
   */
  @Override
  public void handleCommand(String command) {
    int[] positions = decodeCommand(command);
    Pawn pawn = cells[positions[0]][positions[1]].getPawn();
    cells[positions[0]][positions[1]].pawnMoveOut();
    cells[positions[2]][positions[3]].pawnMoveIn(pawn);
  }

  @Override
  public void handleCreate(String command) {
    int[] positions = decodeCommand(command);
    int row = positions[0];
    int col = positions[1];
    int player = positions[2];
    int numPlayers = positions[3];
    Color color = Color.valueOf(ColorManager.getDefaultColorString(numPlayers,player));
    setupPawn(player, color, cells[row][col]);
  }

  protected abstract void setupPawn(int playerNum, Color color, Cell cell);

  /**
   * Decodes a command string into an array of integers representing positions and parameters.
   *
   * @param command The command string to decode.
   * @return An array of integers representing decoded positions and parameters.
   */
  private int[] decodeCommand(String command) {
    String[] tokens = command.split(" ");
    int[] result = new int[tokens.length - 1];
    try {
      for (int i = 1; i < tokens.length; i++) {
        result[i - 1] = Integer.parseInt(tokens[i]);
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Sets the win by making board not clickable.
   */
  @Override
  public void setWin() {
    for (int i = 0; i < boardHeight; i++) {
      for (int j = 0; j < boardWidth; j++) {
        cells[i][j].setOnMouseClicked(null);
      }
    }
  }

  /**
   * Returns the 2D array of cells representing the board.
   *
   * @return A 2D array of {@link Cell} objects.
   */
  public Cell[][] getCells() {
    return cells;
  }
}
