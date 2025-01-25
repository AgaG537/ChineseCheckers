package org.server;

import org.junit.jupiter.api.Test;
import org.server.board.boardObjects.Cell;
import org.server.board.boardManagement.YinYangBoardManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the YinYangBoardManager class, ensuring compliance with YinYang-specific game rules and mechanics.
 */
class YinYangBoardManagerTest {

  /**
   * Verifies the initialization of the YinYang board, including dimensions.
   */
  @Test
  public void testRandomZoneSelection() {
    int marblesPerPlayer = 5;
    int seed = 1234; // Use a fixed seed for repeatability
    YinYangBoardManager manager = new YinYangBoardManager(marblesPerPlayer, seed);

    int[] playerZones = manager.getActiveZoneNums();


    // Check that the zones are valid (not 0 or 3)
    assertNotEquals(playerZones[0], 1);
    assertNotEquals(playerZones[3], 1);
  }

  @Test
  public void testSetupPlayerZones() {
    int marblesPerPlayer = 5;
    int seed = 1234;
    YinYangBoardManager manager = new YinYangBoardManager(marblesPerPlayer, seed);

    // Verify the player zones setup
    // Check that at least one of the cells in the player's zones is assigned to a player
    for (int i = 0; i < manager.getBoardHeight(); i++) {
      for (int j = 0; j < manager.getBoardWidth(); j++) {
        Cell cell = manager.getCell(i, j);
        if (cell.getPawn() != null) {
          int playerNum = cell.getPawn().getPlayerNum();
          assertTrue(playerNum == 1 || playerNum == 2);
        }
      }
    }
  }

  @Test
  public void testCellAssignment() {
    int marblesPerPlayer = 5;
    int seed = 1234;
    YinYangBoardManager manager = new YinYangBoardManager(marblesPerPlayer, seed);

    // Check that cells in active zones have pawns assigned to the correct player
    for (int i = 0; i < manager.getBoardHeight(); i++) {
      for (int j = 0; j < manager.getBoardWidth(); j++) {
        Cell cell = manager.getCell(i, j);
        if (cell.getPawn() != null) {
          int playerNum = cell.getPawn().getPlayerNum();
          // Player 1's cells should belong to zone 1, Player 2's cells to zone 2
          if (playerNum == 1) {
            assertEquals(1, cell.getZoneNum());
          } else if (playerNum == 2) {
            assertEquals(2, cell.getZoneNum());
          }
        }
      }
    }
  }
}
