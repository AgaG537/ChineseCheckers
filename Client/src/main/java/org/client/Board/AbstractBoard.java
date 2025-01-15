package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Abstract class representing the common functionality for managing different board types.
 * Provides the structure for initializing the board.
 */
public abstract class AbstractBoard implements Board {
  protected final int playerZoneHeight;
  protected final int boardHeight;
  protected final int boardWidth;
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
    setupPlayerZones(numOfPlayers);
  }

  /**
   * Abstract method for setting up player zones.
   * Must be implemented by subclasses.
   *
   * @param numOfPlayers The number of players in the game.
   */
  protected abstract void setupPlayerZones(int numOfPlayers);


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
