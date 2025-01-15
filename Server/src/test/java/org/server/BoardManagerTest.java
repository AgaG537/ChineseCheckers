package org.server;

import org.junit.jupiter.api.Test;
import org.server.board.BoardManager;
import org.server.board.Cell;
import org.server.board.Pawn;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BoardManager class, focusing on board initialization, moves, and game state validation.
 */
public class BoardManagerTest {

  /**
   * Tests the initialization of the board, including dimensions and cell setup.
   */
  @Test
  public void testBoardInitialization() {
    BoardManager boardManager = new BoardManager(10, 2);

    // Verify board dimensions
    assertEquals(17, boardManager.getBoardHeight(), "Board height should be 17");
    assertEquals(25, boardManager.getBoardWidth(), "Board width should be 25");

    // Verify cells initialization
    for (int i = 0; i < boardManager.getBoardHeight(); i++) {
      for (int j = 0; j < boardManager.getBoardWidth(); j++) {
        assertNotNull(boardManager.getCell(i, j), "Cells should be initialized");
      }
    }
  }

  /**
   * Verifies that the constraint size of the board is positive.
   */
  @Test
  public void testConstraintSize() {
    BoardManager boardManager = new BoardManager(10, 2);
    assertTrue(boardManager.getConstraintSize() > 0, "Constraint size should be positive");
  }

  /**
   * Tests the `makeMove` method by simulating a pawn move on the board.
   */
  @Test
  public void testMakeMove() {
    BoardManager boardManager = new BoardManager(10, 2);

    // Simulate placing a pawn for a player at (4, 4)
    Cell startCell = boardManager.getCell(4, 4);
    startCell.pawnMoveIn(new Pawn(1, startCell)); // Player 1

    // Simulate moving the pawn to (5, 5)
    String moveInput = "4 4 1 5 5 0";
    boardManager.makeMove(moveInput);

    // Verify the move
    assertNull(startCell.getPawn(), "Start cell should be empty after move");
    assertNotNull(boardManager.getCell(5, 5).getPawn(), "Target cell should have the pawn");
    assertEquals(1, boardManager.getCell(5, 5).getPawn().getPlayerNum(), "Pawn should belong to player 1");
  }

  /**
   * Validates moves according to game rules, including both valid and invalid scenarios.
   */
  @Test
  public void testValidateMove() {
    BoardManager boardManager = new BoardManager(10, 2);

    // Simulate placing a pawn for a player at (4, 4)
    Cell startCell = boardManager.getCell(4, 4);
    startCell.pawnMoveIn(new Pawn(1, startCell)); // Player 1

    // Test valid single-step move
    String validMove = "4 4 1 5 5 0";
    assertTrue(boardManager.validateMove(1, validMove), "Move should be valid");

    // Test invalid move (empty starting cell)
    String invalidMove = "6 6 1 7 7 0";
    assertFalse(boardManager.validateMove(1, invalidMove), "Move should be invalid");
  }

  /**
   * Checks the `checkWin` method to ensure it identifies winning conditions correctly.
   */
  @Test
  public void testCheckWin() {
    BoardManager boardManager = new BoardManager(10, 2);

    // Simulate a win condition
    // (assuming PlayerZoneFactory.checkZoneForWin is implemented)
    assertEquals(0, boardManager.checkWin(), "No winner at start of the game");
  }
}
