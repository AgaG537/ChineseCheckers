package org.client;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.client.Board.boardManagement.AbstractBoard;
import org.client.Board.boardObjects.Cell;
import org.client.Board.boardManagement.OrderBoard;
import org.client.Board.boardManagement.StandardBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractBoardTest {

  private AbstractBoard orderBoard;
  private AbstractBoard standardBoard;

  @BeforeEach
  void setup() {
    orderBoard = new OrderBoard(10, 2); // 10 marbles, 2 players
    standardBoard = new StandardBoard(10, 6); // 10 marbles, 6 players
  }

  @Test
  void testBoardDimensions() {
    assertEquals(17, orderBoard.getBoardHeight()); // Expected based on player zone height and formula
    assertEquals(25, orderBoard.getBoardWidth());
    assertEquals(17, standardBoard.getBoardHeight());
    assertEquals(25, standardBoard.getBoardWidth());
  }

  @Test
  void testPlayerZoneSetup() {
    assertNotNull(orderBoard.getCells());
    assertNotNull(standardBoard.getCells());

    Cell[][] cells = orderBoard.getCells();
    // Check that valid positions are initialized
    assertTrue(cells[9][11].isInsideBoard());
    assertFalse(cells[0][0].isInsideBoard());
  }

  @Test
  void testAssignPawnToCell() {
    Cell cell = orderBoard.getCell(0, 6);
    orderBoard.assignPawnToCell(cell, 1, Color.RED);

    assertNotNull(cell.getPawn());
    assertEquals(1, cell.getPawn().getPlayerNum());
    assertEquals(Color.RED, cell.getPawn().getColor());
  }


  @Test
  void testHandleCommand() {
    Cell startCell = orderBoard.getCell(0, 6);
    Cell endCell = orderBoard.getCell(8, 6);

    orderBoard.assignPawnToCell(startCell, 1, Color.BLUE);
    assertNotNull(startCell.getPawn());
    assertNull(endCell.getPawn());

    orderBoard.handleCommand("MOVE 0 6 8 6");
    assertNull(startCell.getPawn());
    assertNotNull(endCell.getPawn());
    assertEquals(1, endCell.getPawn().getPlayerNum());
  }

  @Test
  void testHandleCreate() {
    orderBoard.handleCreate("CREATE 0 6 1 2");
    Cell cell = orderBoard.getCell(0, 6);
    assertNotNull(cell.getPawn());
    assertEquals(1, cell.getPawn().getPlayerNum());
    assertEquals(Color.BLACK, cell.getPawn().getColor());
  }

  @Test
  void testCalculatePlayerZoneHeight() {
    assertEquals(4, orderBoard.calculatePlayerZoneHeight(10)); // Sum of 1 + 2 + 3 + 4 = 10
    assertEquals(0, orderBoard.calculatePlayerZoneHeight(11)); // No matching height
  }

  @Test
  void testSetWin() {
    orderBoard.setWin();
    Cell[][] cells = orderBoard.getCells();

    for (int i = 0; i < orderBoard.getBoardHeight(); i++) {
      for (int j = 0; j < orderBoard.getBoardWidth(); j++) {
        assertNull(cells[i][j].getOnMouseClicked());
      }
    }
  }
}
