package org.client;

import javafx.scene.paint.Color;
import org.client.Board.Cell;
import org.client.Board.ColorManager;
import org.client.Board.YinYangBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YinYangBoardTest {

  private YinYangBoard yinYangBoard;

  @BeforeEach
  void setUp() {
    // Create a YinYangBoard instance with 10 marbles per player
    yinYangBoard = new YinYangBoard(10);
  }

  @Test
  void testBoardDimensions() {
    assertEquals(17, yinYangBoard.getBoardHeight()); // Adjust to the correct height
    assertEquals(25, yinYangBoard.getBoardWidth()); // Adjust to the correct width
  }


  @Test
  void testPlayerZoneHeight() {
    // Verify the player zone height calculation
    assertEquals(4, yinYangBoard.calculatePlayerZoneHeight(10));
  }

  @Test
  void testCellsInitialization() {
    // Verify all cells are initialized
    Cell[][] cells = yinYangBoard.getCells();
    for (int i = 0; i < yinYangBoard.getBoardHeight(); i++) {
      for (int j = 0; j < yinYangBoard.getBoardWidth(); j++) {
        assertNotNull(cells[i][j]);
      }
    }
  }




  @Test
  void testAssignPawnToCell() {
    // Verify assigning a pawn to a cell
    Cell[][] cells = yinYangBoard.getCells();
    Cell cell = cells[10][15];

    yinYangBoard.assignPawnToCell(cell, 1, Color.RED);

    assertNotNull(cell.getPawn());
    assertEquals(1, cell.getPawn().getPlayerNum());
    assertEquals(Color.RED, cell.getPawn().getColor());
  }

  @Test
  void testHandleCreateCommand() {
    // Verify handling a create command for setting up a pawn
    String command = "create 10 15 1 2";
    yinYangBoard.handleCreate(command);

    Cell cell = yinYangBoard.getCell(10, 15);
    assertNotNull(cell.getPawn());
    assertEquals(1, cell.getPawn().getPlayerNum());
    assertEquals(ColorManager.generateDefaultColor(1), cell.getPawn().getColor());
  }

  @Test
  void testHandleMoveCommand() {
    // Verify handling a move command
    Cell[][] cells = yinYangBoard.getCells();
    Cell sourceCell = cells[10][15];
    Cell targetCell = cells[11][17];

    yinYangBoard.assignPawnToCell(sourceCell, 1, Color.BLUE);
    String command = "move 10 15 11 17";
    yinYangBoard.handleCommand(command);

    assertNull(sourceCell.getPawn());
    assertNotNull(targetCell.getPawn());
    assertEquals(1, targetCell.getPawn().getPlayerNum());
    assertEquals(Color.BLUE, targetCell.getPawn().getColor());
  }

  @Test
  void testSetWin() {
    // Verify the board becomes non-clickable after a win
    yinYangBoard.setWin();
    Cell[][] cells = yinYangBoard.getCells();

    for (Cell[] row : cells) {
      for (Cell cell : row) {
        assertNull(cell.getOnMouseClicked());
      }
    }
  }

  @Test
  void testInvalidPlayerZoneHeight() {
    // Verify that an invalid number of marbles returns 0 for the zone height
    assertEquals(0, yinYangBoard.calculatePlayerZoneHeight(7));
  }
}
