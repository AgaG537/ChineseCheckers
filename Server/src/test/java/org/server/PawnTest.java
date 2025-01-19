package org.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.server.board.Cell;
import org.server.board.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {

  private Cell cell1;
  private Cell cell2;
  private Pawn pawn;

  @BeforeEach
  public void setUp() {
    // Set up initial cells and pawn before each test
    cell1 = new Cell(0, 0); // Row 0, Column 0
    cell2 = new Cell(1, 1); // Row 1, Column 1
    pawn = new Pawn(1, cell1); // Create a pawn for player 1 and place it in cell1
  }

  @Test
  public void testPawnInitialization() {
    // Check if the pawn is initialized correctly
    assertEquals(1, pawn.getPlayerNum(), "Player number should be 1");
    assertEquals(cell1, pawn.getCurrCell(), "Pawn should be placed in cell1 initially");
  }

  @Test
  public void testSetCurrCell() {
    // Move pawn to cell2
    pawn.setCurrCell(cell2);

    // Check if the pawn is now in cell2
    assertEquals(cell2, pawn.getCurrCell(), "Pawn should be moved to cell2");
    assertTrue(cell2.isOccupied(), "Cell2 should be occupied after moving the pawn there");
  }


}
