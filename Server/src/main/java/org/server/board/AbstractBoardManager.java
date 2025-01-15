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
  protected Cell[][] cells;

  protected final int constraintSize;

  /**
   * Constructor for the BoardManager class.
   *
   * @param marblesPerPlayer The number of marbles per player.
   * @param numOfPlayers     The number of players in the game.
   */
  public AbstractBoardManager(int marblesPerPlayer, int numOfPlayers) {
    playerZoneHeight = countPlayerZoneHeight(marblesPerPlayer);
    boardHeight = playerZoneHeight * 4 + 1;
    boardWidth = (playerZoneHeight * 6) + 1;
    constraintSize = (1000 / boardWidth) / 2;

    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();
    initializeNeighbors();

    PlayerZoneFactory playerZoneFactory = new PlayerZoneFactory(numOfPlayers, boardWidth, boardHeight, playerZoneHeight);
    setupPlayerZones(numOfPlayers);
  }

  /**
   * Factory method to set up player zones. The specific implementation
   * is determined by subclasses based on the board type.
   *
   * @param numOfPlayers The number of players in the game.
   */
  protected abstract void setupPlayerZones(int numOfPlayers);

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

  /**
   * Executes a move by relocating a pawn from a starting cell to a target cell.
   *
   * @param input The move details encoded as a string (e.g., "rowStart colStart rowEnd colEnd").
   * @return A command string describing the move made.
   */
  @Override
  public String makeMove(String input) {
    int[] moveDetails = decodeInput(input);
    int rowStart = moveDetails[0];
    int colStart = moveDetails[1];
    int rowEnd = moveDetails[3];
    int colEnd = moveDetails[4];

    Pawn pawn = cells[rowStart][colStart].getPawn();
    cells[rowStart][colStart].pawnMoveOut();
    cells[rowEnd][colEnd].pawnMoveIn(pawn);
    return "[CMD] " + rowStart + " " + colStart + " " + rowEnd + " " + colEnd;
  }

  /**
   * Validates if a move is legal based on the game rules.
   *
   * @param userNum The user number making the move.
   * @param input   The move details encoded as a string (e.g., "startRow startCol endRow endCol").
   * @return True if the move is valid; false otherwise.
   */
  @Override
  public boolean validateMove(int userNum, String input) {
    int[] moveDetails = decodeInput(input);
    int startRow = moveDetails[0];
    int startCol = moveDetails[1];
    int startPlayer = moveDetails[2];
    int endRow = moveDetails[3];
    int endCol = moveDetails[4];
    int endPlayer = moveDetails[5];

    System.out.printf("Player: %d clicked pawn: %d\n", userNum, startPlayer);
    System.out.printf("Start: [%d, %d], End: [%d, %d]\n", startRow, startCol, endRow, endCol);

    // Validate starting and ending positions
    if (!isValidStartingPoint(userNum, startPlayer) || !isValidEndingPoint(endPlayer)) {
      return false;
    }

    if (cells[startRow][startCol].getPawn() == null) {
      return false;
    }

    // Directions for movement
    int[][] directions = {
        {-1, -1}, {0, -2}, {1, -1},  // Upper left, left, bottom left
        {1, 1}, {0, 2}, {-1, 1}      // Bottom right, right, upper right
    };

    ArrayList<int[]> visited = new ArrayList<>();

    // Check single-step moves
    if (isValidSingleStep(startRow, startCol, endRow, endCol, directions)) {
      return true;
    }

    // Check jumps
    return isValidJump(startRow, startCol, endRow, endCol, directions, visited);
  }

  /**
   * Checks if the starting position of the move belongs to the current player.
   *
   * @param userNum    The player number attempting the move.
   * @param startPlayer The player number associated with the pawn at the start position.
   * @return True if the starting position is valid; false otherwise.
   */
  private boolean isValidStartingPoint(int userNum, int startPlayer) {
    if (startPlayer != userNum) {
      System.out.println("Invalid start: Not your pawn.");
      return false;
    }
    return true;
  }

  /**
   * Checks if the ending position of the move is unoccupied.
   *
   * @param endPlayer The player number at the target position (0 if unoccupied).
   * @return True if the ending position is valid; false otherwise.
   */
  private boolean isValidEndingPoint(int endPlayer) {
    if (endPlayer != 0) {
      System.out.println("Invalid end: Target cell is occupied.");
      return false;
    }
    return true;
  }

  /**
   * Validates a single-step move by checking if the target cell is adjacent and unoccupied.
   *
   * @param startRow   Starting row index.
   * @param startCol   Starting column index.
   * @param endRow     Target row index.
   * @param endCol     Target column index.
   * @param directions Possible movement directions for the pawn.
   * @return True if the move is a valid single-step move; false otherwise.
   */
  private boolean isValidSingleStep(int startRow, int startCol, int endRow, int endCol, int[][] directions) {
    for (int[] direction : directions) {
      int targetRow = startRow + direction[0];
      int targetCol = startCol + direction[1];
      if (targetRow == endRow && targetCol == endCol && !cells[targetRow][targetCol].isOccupied()) {
        System.out.println("Valid single-step move.");
        return true;
      }
    }
    return false;
  }

  /**
   * Validates a jump move by checking if the target cell is reachable via a valid jump.
   *
   * @param startRow Starting row index.
   * @param startCol Starting column index.
   * @param endRow   Target row index.
   * @param endCol   Target column index.
   * @param directions Possible jump directions for the pawn.
   * @param visited  A list of already visited cells to avoid cycles.
   * @return True if the move is a valid jump move; false otherwise.
   */
  private boolean isValidJump(int startRow, int startCol, int endRow, int endCol, int[][] directions, ArrayList<int[]> visited) {
    for (int[] direction : directions) {
      int jumpOverRow = startRow + direction[0];
      int jumpOverCol = startCol + direction[1];

      // Check if the adjacent cell is within bounds and occupied
      if (isWithinBounds(jumpOverRow, jumpOverCol) && cells[jumpOverRow][jumpOverCol].isOccupied()) {
        int landingRow = jumpOverRow + direction[0];
        int landingCol = jumpOverCol + direction[1];

        // Check if the landing cell is within bounds and unoccupied
        if (isWithinBounds(landingRow, landingCol) && !cells[landingRow][landingCol].isOccupied()) {
          if (landingRow == endRow && landingCol == endCol) {
            System.out.println("Valid jump move.");
            return true;
          }

          // Add to visited and recurse
          visited.add(new int[]{startRow, startCol});
          if (validateMoveRecursively(landingRow, landingCol, endRow, endCol, visited)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Recursively validates multi-jump moves by exploring all possible jump paths.
   *
   * @param currentRow Current row index during recursion.
   * @param currentCol Current column index during recursion.
   * @param endRow     Target row index.
   * @param endCol     Target column index.
   * @param visited    A list of already visited cells to avoid cycles.
   * @return True if the end cell is reachable via valid jumps; false otherwise.
   */
  private boolean validateMoveRecursively(int currentRow, int currentCol, int endRow, int endCol, ArrayList<int[]> visited) {
    if (cells[currentRow][currentCol].isOccupied()) {
      System.out.println("Invalid recursive move: Current cell is occupied.");
      return false;
    }
    if (currentRow == endRow && currentCol == endCol) {
      System.out.println("Recursive move successful.");
      return true;
    }
    if (visited.stream().anyMatch(cell -> Arrays.equals(cell, new int[]{currentRow, currentCol}))) {
      System.out.println("Invalid recursive move: Already visited this cell.");
      return false;
    }

    visited.add(new int[]{currentRow, currentCol});
    int[][] directions = {
        {-1, -1}, {0, -2}, {1, -1},  // Upper left, left, bottom left
        {1, 1}, {0, 2}, {-1, 1}      // Bottom right, right, upper right
    };

    for (int[] direction : directions) {
      int jumpOverRow = currentRow + direction[0];
      int jumpOverCol = currentCol + direction[1];
      if (isWithinBounds(jumpOverRow, jumpOverCol) && cells[jumpOverRow][jumpOverCol].isOccupied()) {
        int landingRow = jumpOverRow + direction[0];
        int landingCol = jumpOverCol + direction[1];
        if (isWithinBounds(landingRow, landingCol) && !cells[landingRow][landingCol].isOccupied()) {
          if (validateMoveRecursively(landingRow, landingCol, endRow, endCol, visited)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Checks if a cell coordinate is within the bounds of the board.
   *
   * @param row The row index.
   * @param col The column index.
   * @return True if the cell is within bounds; false otherwise.
   */
  private boolean isWithinBounds(int row, int col) {
    return row >= 0 && row < cells.length && col >= 0 && col < cells[0].length;
  }

  /**
   * Decodes a move input string into an array of integers representing the move details.
   *
   * @param input The move input as a string.
   * @return An array of integers containing the parsed move details.
   */
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

  /**
   * Checks the player zones for a win condition.
   *
   * @return The player number of the winner, or 0 if no winner is found.
   */
  public abstract int checkWin();

  /**
   * Retrieves the 2D array of cells representing the board.
   *
   * @return A 2D array of Cell objects.
   */
  public Cell[][] getCells() {
    return cells;
  }

}

