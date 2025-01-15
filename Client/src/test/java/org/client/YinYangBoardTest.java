package org.client;

import org.client.Board.Cell;
import org.client.Board.YinYangBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the YinYangBoard class.
 */
class YinYangBoardTest {

  /**
   * Tests that the YinYangBoard initializes with the correct dimensions and properties.
   */
  @Test
  void testYinYangBoardInitialization() {
    int marblesPerPlayer = 6;
    YinYangBoard board = new YinYangBoard(marblesPerPlayer);

    assertNotNull(board, "YinYangBoard should be initialized");
    assertEquals(4 * board.calculatePlayerZoneHeight(marblesPerPlayer) + 1, board.getBoardHeight(),
        "Board height should be calculated correctly");
    assertEquals(6 * board.calculatePlayerZoneHeight(marblesPerPlayer) + 1, board.getBoardWidth(),
        "Board width should be calculated correctly");
  }

  /**
   * Verifies that Yin-Yang player zones are properly set up in the YinYangBoard.
   */
  @Test
  void testPlayerZonesSetup() {
    int marblesPerPlayer = 6;
    YinYangBoard board = new YinYangBoard(marblesPerPlayer);

    Cell[][] cells = board.getCells();
    boolean yinYangZoneFound = false;

    for (Cell[] row : cells) {
      for (Cell cell : row) {
        if (cell.getInitialPlayerNum() > 0) {
          yinYangZoneFound = true;
          break;
        }
      }
    }

    assertTrue(yinYangZoneFound, "Yin-Yang zones should be set up in YinYangBoard");
  }
}
