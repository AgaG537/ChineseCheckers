package org.client;

import javafx.scene.paint.Color;
import org.client.Board.Cell;
import org.client.Board.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

  @Test
  void testPawnInitialization() {
    Cell cell = new Cell(2, 3);
    Pawn pawn = new Pawn(1, Color.GREEN, cell);

    assertEquals(1, pawn.getPlayerNum(), "Player number should be set correctly");
    assertEquals(Color.GREEN, pawn.getColor(), "Pawn color should be set correctly");
    assertEquals(cell, pawn.getCurrCell(), "Initial cell should be set correctly");
    assertTrue(cell.isOccupied(), "Cell should be occupied by pawn");
  }

  @Test
  void testSetCurrCell() {
    Cell initialCell = new Cell(0, 0);
    Cell newCell = new Cell(1, 1);
    Pawn pawn = new Pawn(1, Color.BLUE, initialCell);

    pawn.setCurrCell(newCell);

    assertEquals(newCell, pawn.getCurrCell(), "Pawn's current cell should be updated");
    assertTrue(newCell.isOccupied(), "New cell should be occupied by pawn");
  }

  @Test
  void testPawnToString() {
    Cell cell = new Cell(2, 3);
    Pawn pawn = new Pawn(2, Color.YELLOW, cell);

    String expected = "Pawn, color: 0xffff00ff, playerNum: 2";
    assertEquals(expected, pawn.toString(), "Pawn's string representation should be correct");
  }
}
