package org.client.Board;


import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.Color;

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

  /**
   * Initializes the start points for player zones on the board.
   *
   * @param boardWidth       The total width of the board.
   * @param boardHeight      The total height of the board.
   * @param playerZoneHeight The height of each player's zone.
   * @return A 2D array where each entry represents the starting coordinates for a player zone.
   */
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

  /**
   * Calculates the height of a player's zone based on the number of marbles.
   *
   * @param playerZoneHeight The height of a single player zone.
   * @return The number of cells in a single player zone.
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
   * Marks zones as active or inactive based on the number of players.
   * Updates the active zone numbers array to indicate active zones.
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
   * Colors zones and initializes player numbers in active zones.
   */
  protected void setupPlayerZones() {
    int defaultPlayerNum = 1;

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];
      int k = 0;

      for (int row = rowStart; checkRow(i, row, rowStart); row = advanceRow(i, row)) {
        for (int col = colStart - k; col <= colStart + k; col += 2) {
//          Color color = ColorManager.generateDefaultColor(i + 1);
          Color color = getColor(i+1);
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


  /**
   * Function generating a Color object based on player number
   *
   * @param colorNum integer number associated with the desired color
   * @return Color object associated with the given integer
   */
  protected Color getColor(int colorNum) {
    return ColorManager.generateDefaultColor(colorNum);
  }


  /**
   * Verifies whether the current row index is within the bounds of the player zone.
   *
   * @param i         The index of the player zone.
   * @param row       The current row index.
   * @param rowStart  The starting row index for the zone.
   * @return True if the row index is within the bounds of the zone; false otherwise.
   */
  protected boolean checkRow(int i, int row, int rowStart) {
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
  protected int advanceRow(int i, int row) {
    if (i % 2 == 0) {
      return row + 1;
    } else {
      return row - 1;
    }
  }

  /**
   * Assigns a cell to a specific player zone, setting its flag, player number, and color.
   *
   * @param cell       The cell to assign.
   * @param color      The color of the zone.
   * @param playerNum  The player number associated with the zone.
   */
  protected void assignCellToZone(Cell cell, Color color, int playerNum) {
    cell.setFlag(5);
    cell.setInitialPlayerNum(playerNum);
    cell.setZoneColor(color);
  }

  /**
   * Assigns a pawn to a specific cell, setting its player number and color.
   *
   * @param cell       The cell to assign.
   * @param playerNum  The player number associated with the pawn.
   * @param color      The color of the pawn.
   */
  protected void assignPawnToCell(Cell cell, int playerNum, Color color) {
    if (playerNum != 0) {
      System.out.println("assignPawnToCell color: " + color);
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
   * Returns the size constraint used for cell rendering in the GUI.
   *
   * @return The size constraint used for cell rendering in the GUI.
   */
  @Override
  public int getConstraintSize() {
    return constraintSize;
  }

  /**
   * Returns the width of the board.
   *
   * @return The width of the board.
   */
  @Override
  public int getBoardWidth() {
    return boardWidth;
  }

  /**
   * Returns the height of the board.
   *
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
   * Handles a command to create game elements such as pawns or zones on the board.
   *
   * @param command The command string specifying creation instructions.
   */
  @Override
  public void handleCreate(String command) {
    int[] positions = decodeCommand(command);
    int row = positions[0];
    int col = positions[1];
    int player = positions[2];
    int numPlayers = positions[3];
    Color color = Color.valueOf(ColorManager.getDefaultColorString(numPlayers, player));
    setupPawn(player, color, cells[row][col]);
  }

  /**
   * Configures the cell with the pawn's properties.
   *
   * @param playerNum The number of the player owning the pawn.
   * @param color     The color associated with the pawn.
   * @param cell      The cell where the pawn is to be placed.
   */
  public abstract void setupPawn(int playerNum, Color color, Cell cell);

  /**
   * Decodes a command string into an array of integers representing positions and parameters.
   *
   * @param command The command string to decode.
   * @return An array of integers representing decoded positions and parameters.
   */
  protected int[] decodeCommand(String command) {
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
