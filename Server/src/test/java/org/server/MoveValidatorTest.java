package org.server;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.server.board.Cell;
import org.server.board.MoveValidator;
import org.server.board.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class MoveValidatorTest {

  private Cell[][] cells;
  private MoveValidator moveValidator;
  private Pawn pawnPlayer1;
  private Pawn pawnPlayer2;

  @BeforeEach
  public void setUp() {
    // Set up the board (example: 5x5 grid)
    cells = new Cell[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        cells[i][j] = new Cell(i, j);
      }
    }

    // Create pawns and place them on the board
    pawnPlayer1 = new Pawn(1, cells[2][2]);
    pawnPlayer2 = new Pawn(2, cells[3][3]);

    // Initialize moveValidator with the board
    moveValidator = new MoveValidator(cells);
  }

  @Test
  public void testValidateMove_SingleStepValid() {
    String moveInput = "2 2 1 1 1 0";
    boolean result = moveValidator.validateMove(1, moveInput);
    assertTrue(result, "Single step move should be valid.");
  }

  @Test
  public void testValidateMove_SingleStepOccupied() {
    // Invalid single step move: Player 1 tries to move to an occupied cell (3,3)
    String moveInput = "2 2 1 3 3 2"; // Start: (2,2), End: (3,3), but (3,3) is occupied by Player 2
    boolean result = moveValidator.validateMove(1, moveInput);
    assertFalse(result, "Move should be invalid, target cell is occupied.");
  }

  @Test
  public void testValidateMove_JumpValid() {
    // Valid jump move: Player 1 jumps over Player 2
    String moveInput = "2 2 1 4 4 0"; // Start: (2,2), Jump over (3,3) to (4,4)
    boolean result = moveValidator.validateMove(1, moveInput);
    assertTrue(result, "Jump move should be valid.");
  }

  @Test
  public void testValidateMove_JumpInvalidTarget() {
    // Invalid jump move: Player 1 tries to jump but target (4,4) is occupied
    String moveInput = "2 2 1 4 4 2"; // Start: (2,2), Jump over (3,3) to (4,4), but (4,4) is occupied by Player 2
    boolean result = moveValidator.validateMove(1, moveInput);
    assertFalse(result, "Move should be invalid, target cell is occupied.");
  }

  @Test
  public void testMakeMove() {
    // Execute a valid move and check the result
    String moveInput = "2 2 1 2 3 0"; // Start: (2,2), End: (2,3)
    String result = moveValidator.makeMove(moveInput);
    String expected = "[CMD] 2 2 2 3";
    assertEquals(expected, result, "Move command should be correctly formatted.");
    assertNull(cells[2][2].getPawn(), "Start cell should be empty after move.");
    assertNotNull(cells[2][3].getPawn(), "End cell should contain the pawn after move.");
  }

  @Test
  public void testInvalidMove_OutsideBounds() {
    // Invalid move: Trying to move outside the bounds of the board
    String moveInput = "0 0 1 -1 0 0"; // Invalid row: -1
    boolean result = moveValidator.validateMove(1, moveInput);
    assertFalse(result, "Move should be invalid as it's outside board bounds.");
  }

  @Test
  public void testValidateMove_InvalidPlayer() {
    // Invalid move: Player 1 tries to move Player 2's pawn
    String moveInput = "2 2 2 2 3 0"; // Player 1 tries to move Player 2's pawn
    boolean result = moveValidator.validateMove(1, moveInput);
    assertFalse(result, "Move should be invalid as it's not Player 1's pawn.");
  }
}
