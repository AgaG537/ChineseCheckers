package org.server.board.moveManagement;

import org.server.board.boardObjects.Cell;
import org.server.board.boardObjects.Pawn;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Responsible for validating and executing moves in the game.
 * Validates whether a move is allowed based on the game rules and moves the corresponding pawn.
 */
public class MoveValidator implements IMoveValidator {

  private final Cell[][] cells;
  int[][] directions;

  /**
   * Constructs a move validator with specified move checking methods' implementations.
   *
   * @param cells The cells representing a game board.
   */
  public MoveValidator(Cell[][] cells) {
    this.cells = cells;
    directions = new int[][]{
        {-1, -1}, {0, -2}, {1, -1},  // Upper left, left, bottom left
        {1, 1}, {0, 2}, {-1, 1}      // Bottom right, right, upper right
    };
  }

  /**
   * Executes a move by relocating a pawn from a starting cell to a target cell.
   *
   * @param input The move details encoded as a string (e.g., "rowStart colStart rowEnd colEnd").
   * @return A command string describing the move made (e.g., "[CMD] rowStart colStart rowEnd colEnd").
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

    ArrayList<int[]> visited = new ArrayList<>();

    // Check single-step moves
    if (isValidSingleStep(startRow, startCol, endRow, endCol)) {
      return true;
    }

    // Check jumps
    return isValidJump(startRow, startCol, endRow, endCol, visited);
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
   * @return True if the move is a valid single-step move; false otherwise.
   */
  private boolean isValidSingleStep(int startRow, int startCol, int endRow, int endCol) {
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
   * @param visited  A list of already visited cells to avoid cycles.
   * @return True if the move is a valid jump move; false otherwise.
   */
  private boolean isValidJump(int startRow, int startCol, int endRow, int endCol, ArrayList<int[]> visited) {
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
}
