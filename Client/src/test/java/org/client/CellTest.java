package org.client;

import javafx.scene.paint.Color;
import org.client.Board.Cell;
import org.client.Board.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

  @Test
  void testCellInitialization() {
    Cell cell = new Cell(5, 10);

    assertEquals(5, cell.getRow(), "Row should be initialized correctly");
    assertEquals(10, cell.getCol(), "Column should be initialized correctly");
    assertFalse(cell.isInsideBoard(), "Cell should not be inside the board initially");
    assertFalse(cell.isOccupied(), "Cell should not be occupied initially");
    assertEquals(0, cell.getInitialPlayerNum(), "Initial player number should be 0");
    assertEquals(Color.TRANSPARENT, cell.getZoneColor(), "Zone color should be transparent by default");
    assertEquals(Color.TRANSPARENT, cell.getCurrentColor(), "Current color should be transparent initially");
  }

  @Test
  void testSetInsideBoard() {
    Cell cell = new Cell(0, 0);

    assertFalse(cell.isInsideBoard(), "Cell should not be inside the board initially");
    cell.setInsideBoard();
    assertTrue(cell.isInsideBoard(), "Cell should be inside the board after being set");
  }

  @Test
  void testSetAndGetZoneColor() {
    Cell cell = new Cell(0, 0);
    Color zoneColor = Color.BLUE;

    cell.setZoneColor(zoneColor);
    assertEquals(zoneColor, cell.getZoneColor(), "Zone color should be set correctly");
  }

  @Test
  void testPawnMoveInAndOut() {
    Cell cell = new Cell(1, 1);
    Pawn pawn = new Pawn(1, Color.RED, cell);

    assertTrue(cell.isOccupied(), "Cell should be occupied after pawn moves in");
    assertEquals(pawn, cell.getPawn(), "Pawn should be set correctly in the cell");

    cell.pawnMoveOut();
    assertFalse(cell.isOccupied(), "Cell should not be occupied after pawn moves out");
    assertNull(cell.getPawn(), "Pawn should be null after moving out");
  }

  @Test
  void testSetFlag() {
    Cell cell = new Cell(0, 0);

    assertEquals(0, cell.getFlag(), "Flag should be 0 initially");
    cell.setFlag(3);
    assertEquals(3, cell.getFlag(), "Flag should be updated correctly");
  }
}
