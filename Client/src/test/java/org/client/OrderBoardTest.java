package org.client;

import org.client.Board.Cell;
import org.client.Board.OrderBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OrderBoard class.
 */
class OrderBoardTest {

  /**
   * Tests that the OrderBoard initializes with the correct dimensions and properties.
   */
  @Test
  void testOrderBoardInitialization() {
    int marblesPerPlayer = 10;
    int numOfPlayers = 4;
    OrderBoard board = new OrderBoard(marblesPerPlayer, numOfPlayers);

    assertNotNull(board, "OrderBoard should be initialized");
    assertEquals(4 * board.calculatePlayerZoneHeight(marblesPerPlayer) + 1, board.getBoardHeight(),
        "Board height should be calculated correctly");
    assertEquals(6 * board.calculatePlayerZoneHeight(marblesPerPlayer) + 1, board.getBoardWidth(),
        "Board width should be calculated correctly");
  }

  /**
   * Verifies that player zones are properly set up in the OrderBoard.
   */
  @Test
  void testPlayerZonesSetup() {
    int marblesPerPlayer = 10;
    int numOfPlayers = 4;
    OrderBoard board = new OrderBoard(marblesPerPlayer, numOfPlayers);

    Cell[][] cells = board.getCells();
    boolean zoneFound = false;

    for (Cell[] row : cells) {
      for (Cell cell : row) {
        if (cell.getInitialPlayerNum() > 0) {
          zoneFound = true;
          break;
        }
      }
    }

    assertTrue(zoneFound, "Player zones should be set up in OrderBoard");
  }
}
