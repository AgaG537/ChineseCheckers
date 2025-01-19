package org.server;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.server.board.Cell;
import org.server.board.MoveValidator;
import org.server.board.Pawn;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveValidatorTest {
  private Cell[][] cells;
  private MoveValidator moveValidator;

  @BeforeEach
  void setUp() {
    // Initialize a small board for testing
    int boardHeight = 17;
    int boardWidth = 25;

    cells = new Cell[boardHeight][boardWidth];
    for (int i = 0; i < boardHeight; i++) {
      for (int j = 0; j < boardWidth; j++) {
        cells[i][j] = new Cell(i, j);
      }
    }

    // Mark cells as valid positions inside the board
    // Adjusting logic to consider top-left as (0, 0) and mark valid cells accordingly
    for (int row = 0; row < boardHeight; row++) {
      for (int col = 0; col < boardWidth; col++) {
        if (isWithinTriangularZone(row, col)) {
          cells[row][col].setInsideBoard();
        }
      }
    }

    // Set up neighbors for cells
    initializeNeighbors();

    // Initialize the MoveValidator
    moveValidator = new MoveValidator(cells);
  }

  // Helper function to determine if a cell is within the triangular zone
  private boolean isWithinTriangularZone(int row, int col) {
    // Define the triangular region
    // Adjust logic to ensure valid indices from the top-left
    int centerCol = (cells[0].length - 1) / 2; // Middle column
    return col >= centerCol - row && col <= centerCol + row && (col - centerCol + row) % 2 == 0;
  }

  private void initializeNeighbors() {
    int[][] directions = {
        {-1, -1}, {-1, 1}, // Top-left, Top-right
        {0, -2}, {0, 2},   // Left, Right
        {1, -1}, {1, 1}    // Bottom-left, Bottom-right
    };

    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        Cell current = cells[i][j];
        if (!current.isInsideBoard()) {
          continue; // Skip invalid positions
        }

        ArrayList<Cell> neighbors = new ArrayList<>();
        for (int[] dir : directions) {
          int ni = i + dir[0];
          int nj = j + dir[1];

          // Check if the calculated indices are within bounds
          if (ni >= 0 && ni < cells.length && nj >= 0 && nj < cells[ni].length) {
            Cell neighbor = cells[ni][nj];
            if (neighbor.isInsideBoard()) {
              neighbors.add(neighbor);
            }
          }
        }
        current.setNeighbors(neighbors);
      }
    }
  }



  @Test
  void testSingleStepMove_valid() {
    // Set up a pawn for player 1
    Pawn pawn = new Pawn(1, cells[8][12]);
    cells[8][12].pawnMoveIn(pawn);

    // Move to a valid adjacent cell
    String input = "8 12 1 7 11 0";
    assertTrue(moveValidator.validateMove(1, input), "Single-step move should be valid.");
  }

  @Test
  void testSingleStepMove_invalidOccupied() {
    // Set up pawns for player 1
    Pawn pawn1 = new Pawn(1, cells[8][12]);
    Pawn pawn2 = new Pawn(2, cells[7][11]);
    cells[8][12].pawnMoveIn(pawn1);
    cells[7][11].pawnMoveIn(pawn2);

    // Attempt to move to an occupied cell
    String input = "8 12 1 7 11 2";
    assertFalse(moveValidator.validateMove(1, input), "Move to occupied cell should be invalid.");
  }

  @Test
  void testJumpMove_valid() {
    // Set up a pawn for player 1 and an intermediate pawn
    Pawn pawn1 = new Pawn(1, cells[8][12]);
    Pawn pawn2 = new Pawn(2, cells[7][11]);
    cells[8][12].pawnMoveIn(pawn1);
    cells[7][11].pawnMoveIn(pawn2);

    // Attempt to jump over the intermediate pawn
    String input = "8 12 1 6 10 0";
    assertTrue(moveValidator.validateMove(1, input), "Jump move should be valid.");
  }

  @Test
  void testJumpMove_invalidNoIntermediatePawn() {
    // Set up a pawn for player 1
    Pawn pawn = new Pawn(1, cells[8][12]);
    cells[8][12].pawnMoveIn(pawn);

    // Attempt to jump without an intermediate pawn
    String input = "8 12 1 6 10 0";
    assertFalse(moveValidator.validateMove(1, input), "Jump move without intermediate pawn should be invalid.");
  }

  @Test
  void testJumpMove_invalidBlocked() {
    // Set up pawns for player 1 and an intermediate pawn
    Pawn pawn1 = new Pawn(1, cells[8][12]);
    Pawn pawn2 = new Pawn(2, cells[7][11]);
    Pawn pawn3 = new Pawn(3, cells[6][10]);
    cells[8][12].pawnMoveIn(pawn1);
    cells[7][11].pawnMoveIn(pawn2);
    cells[6][10].pawnMoveIn(pawn3);

    // Attempt to jump to an already occupied cell
    String input = "8 12 1 6 10 3";
    assertFalse(moveValidator.validateMove(1, input), "Jump to occupied cell should be invalid.");
  }

  @Test
  void testRecursiveJumpMove_valid() {
    // Set up a pawn for player 1 and multiple intermediate pawns
    Pawn pawn1 = new Pawn(1, cells[8][12]);
    Pawn pawn2 = new Pawn(2, cells[7][11]);
    Pawn pawn3 = new Pawn(3, cells[5][9]);
    cells[8][12].pawnMoveIn(pawn1);
    cells[7][11].pawnMoveIn(pawn2);
    cells[5][9].pawnMoveIn(pawn3);

    // Attempt to recursively jump over two pawns
    String input = "8 12 1 6 10 0";
    assertTrue(moveValidator.validateMove(1, input), "Recursive jump move should be valid.");
  }

  @Test
  void testRecursiveJumpMove_invalidIntermediateBlocked() {
    // Set up a pawn for player 1 and an intermediate pawn
    Pawn pawn1 = new Pawn(1, cells[8][12]);
    Pawn pawn2 = new Pawn(2, cells[7][11]);
    Pawn pawn3 = new Pawn(3, cells[6][10]); // Blocking pawn
    cells[8][12].pawnMoveIn(pawn1);
    cells[7][11].pawnMoveIn(pawn2);
    cells[6][10].pawnMoveIn(pawn3);

    // Attempt to recursively jump over two pawns, but second jump is blocked
    String input = "8 12 1 4 8 0";
    assertFalse(moveValidator.validateMove(1, input), "Recursive jump move should be invalid due to blockage.");
  }

  @Test
  void testMultipleJumpsOverThreePawns() {
    // Arrange
    int playerNum = 1;

    // Place pawns for jumps
    Pawn pawn1 = new Pawn(1, cells[12][14]);
    Pawn pawn2 = new Pawn(2, cells[12][12]);
    Pawn pawn3 = new Pawn(2, cells[11][9]);
    Pawn pawn4 = new Pawn(2, cells[9][7]);
    Pawn pawn5 = new Pawn(2, cells[7][7]);

    cells[12][14].pawnMoveIn(pawn1);
    cells[12][12].pawnMoveIn(pawn2);
    cells[11][9].pawnMoveIn(pawn3);
    cells[9][7].pawnMoveIn(pawn4);
    cells[7][7].pawnMoveIn(pawn5);


    // Act
    String moveInput = "12 14 1 6 8 0";
    boolean isValid = moveValidator.validateMove(playerNum, moveInput);
    String moveResult = isValid ? moveValidator.makeMove(moveInput) : "Invalid move";

    // Assert
    assertTrue(isValid, "The move should be valid for multiple jumps over three pawns.");
    assertEquals("[CMD] 12 14 6 8", moveResult, "Move result should match expected command.");
  }

}
