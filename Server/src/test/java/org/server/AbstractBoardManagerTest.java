package org.server;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.server.board.boardManagement.AbstractBoardManager;
import org.server.board.boardObjects.Cell;

import static org.junit.jupiter.api.Assertions.*;

class AbstractBoardManagerTest {

  private AbstractBoardManager boardManager;

  @BeforeEach
  void setUp() {
    boardManager = new AbstractBoardManager(10, 6, 0) {
      @Override
      public void setupCell(Cell cell, int zoneNum, int defaultPlayerNum, int[] activeZoneNums) {
        if (activeZoneNums[zoneNum - 1] == 1) {
          assignCellToZone(cell, zoneNum, defaultPlayerNum);
          assignPawnToCell(cell, defaultPlayerNum);
        } else {
          assignCellToZone(cell, zoneNum, 0);
        }
      }

      @Override
      protected int getTargetPlayer(int numOfPlayers, int zoneNum) {
        return (zoneNum % numOfPlayers) + 1;
      }
    };
  }

  @Test
  void testBoardDimensions() {
    assertEquals(17, boardManager.getBoardHeight());
    assertEquals(25, boardManager.getBoardWidth());
  }


  @Test
  void testCellsInitialization() {
    Cell[][] cells = boardManager.getCells();
    assertNotNull(cells);
    assertEquals(17, cells.length);
    assertEquals(25, cells[0].length);

    // Verify if cells are correctly marked inside the board
    assertTrue(cells[0][12].isInsideBoard());
    assertFalse(cells[0][0].isInsideBoard());
  }

  @Test
  void testSetupPlayerZones() {
    boardManager.setupPlayerZones();

    // Verify player zone setup
    Cell[][] cells = boardManager.getCells();
    Cell targetCell = cells[0][12]; // Central cell of the upper zone
    assertEquals(1, targetCell.getZoneNum());
    assertEquals(1, targetCell.getInitialPlayerNum());
    assertNotNull(targetCell.getPawn());
    assertEquals(1, targetCell.getPawn().getPlayerNum());
  }


  @Test
  void testMakeCreateString() {
    Cell[][] cells = boardManager.getCells();
    Cell cell = cells[0][12]; // Central cell of the upper zone
    String createString = boardManager.makeCreate(0, 12);
    assertEquals("[CREATE] 0 12 0 6", createString);
  }
}
