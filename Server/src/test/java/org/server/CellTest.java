package org.server;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.server.board.Cell;
import org.server.board.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

  private Cell cell;

  @BeforeEach
  public void setUp() {
    // Create a new cell before each test
    cell = new Cell(0, 0); // Row 0, Column 0
  }

  @Test
  public void testCellInitialization() {
    // Check initial state of the cell
    assertEquals(0, cell.getRow(), "Row should be 0");
    assertEquals(0, cell.getCol(), "Column should be 0");
    assertFalse(cell.isInsideBoard(), "Cell should not be inside the board initially");
    assertFalse(cell.isOccupied(), "Cell should not be occupied initially");
    assertNull(cell.getPawn(), "Cell should not have a pawn initially");
    assertEquals(0, cell.getInitialPlayerNum(), "Initial player number should be 0");
    assertEquals(0, cell.getZoneNum(), "Initial zone number should be 0");
  }

  @Test
  public void testSetInsideBoard() {
    // Mark the cell as inside the board and check
    cell.setInsideBoard();
    assertTrue(cell.isInsideBoard(), "Cell should be inside the board after setting");
  }

  @Test
  public void testPawnMoveIn() {
    Pawn pawn = new Pawn(1, cell); // Assuming a simple Pawn constructor takes a player number
    cell.pawnMoveIn(pawn);

    assertTrue(cell.isOccupied(), "Cell should be occupied after pawn move in");
    assertEquals(pawn, cell.getPawn(), "Cell should contain the correct pawn");
  }

  @Test
  public void testPawnMoveOut() {
    Pawn pawn = new Pawn(1, cell);
    cell.pawnMoveIn(pawn);
    cell.pawnMoveOut();

    assertFalse(cell.isOccupied(), "Cell should not be occupied after pawn move out");
    assertNull(cell.getPawn(), "Cell should not contain a pawn after moving out");
  }

  @Test
  public void testSetInitialPlayerNum() {
    cell.setInitialPlayerNum(3);
    assertEquals(3, cell.getInitialPlayerNum(), "Initial player number should be set correctly");
  }

  @Test
  public void testSetZoneNum() {
    cell.setZoneNum(5);
    assertEquals(5, cell.getZoneNum(), "Zone number should be set correctly");
  }
}
