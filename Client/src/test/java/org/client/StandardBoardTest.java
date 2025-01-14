package org.client;

import org.client.Board.Cell;
import org.client.Board.StandardBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StandardBoardTest {

  @Test
  void testStandardBoardInitialization() {
    int marblesPerPlayer = 15;
    int numOfPlayers = 6;
    StandardBoard board = new StandardBoard(marblesPerPlayer, numOfPlayers);

    assertNotNull(board, "StandardBoard should be initialized");
    assertEquals(4 * board.calculatePlayerZoneHeight(marblesPerPlayer) + 1, board.getBoardHeight(),
        "Board height should be calculated correctly");
    assertEquals(6 * board.calculatePlayerZoneHeight(marblesPerPlayer) + 1, board.getBoardWidth(),
        "Board width should be calculated correctly");
  }

  @Test
  void testPlayerZonesSetup() {
    int marblesPerPlayer = 15;
    int numOfPlayers = 6;
    StandardBoard board = new StandardBoard(marblesPerPlayer, numOfPlayers);

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

    assertTrue(zoneFound, "Player zones should be set up in StandardBoard");
  }
}
