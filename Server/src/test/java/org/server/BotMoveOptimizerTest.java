package org.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.server.board.boardObjects.Cell;
import org.server.board.boardObjects.Pawn;
import org.server.board.moveManagement.BotMoveOptimizer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BotMoveOptimizerTest {

  private Cell[][] cells;
  private BotMoveOptimizer optimizer;

  @BeforeEach
  void setup() {
    int boardSize = 5; // Board size: 5x5
    cells = new Cell[boardSize][boardSize];

    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        cells[i][j] = mock(Cell.class);
        when(cells[i][j].isInsideBoard()).thenReturn(true);
        when(cells[i][j].isOccupied()).thenReturn(false);
        when(cells[i][j].getZoneNum()).thenReturn(0);
      }
    }

    optimizer = new BotMoveOptimizer(cells);
  }

  @Test
  void testBestMoveToDestination() {
    int botNum = 1;
    int[] destination = {4, 4};
    int destinationZoneNum = 1;

    when(cells[2][2].isOccupied()).thenReturn(true);
    Pawn botPawn = mock(Pawn.class);
    when(botPawn.getPlayerNum()).thenReturn(botNum);
    when(cells[2][2].getPawn()).thenReturn(botPawn);

    int[] result = optimizer.getBestMoveForBot(botNum, destination, destinationZoneNum);

    assertNotNull(result);
    assertEquals(2, result[0]); // Starting row
    assertEquals(2, result[1]); // Starting column
    // Validate movement towards destination
    assertTrue(result[2] >= 2 && result[3] >= 2, "Bot should move closer to destination.");
  }

  @Test
  void testBotAtDestination() {
    // Arrange: Set up a bot already at its destination
    int botNum = 1;
    int[] destination = {2, 2};
    int destinationZoneNum = 1;

    when(cells[2][2].isOccupied()).thenReturn(true);
    Pawn botPawn = mock(Pawn.class);
    when(botPawn.getPlayerNum()).thenReturn(botNum);
    when(cells[2][2].getPawn()).thenReturn(botPawn);

    // Act: Get the best move
    int[] result = optimizer.getBestMoveForBot(botNum, destination, destinationZoneNum);

    // Assert: Verify the bot stays in place
    assertNotNull(result);
    assertEquals(2, result[0]);
    assertEquals(2, result[1]);
  }

  @Test
  void testBotDoesNotLeaveBoard() {
    int botNum = 1;
    int[] destination = {4, 4};
    int destinationZoneNum = 1;

    when(cells[0][0].isOccupied()).thenReturn(true);
    Pawn botPawn = mock(Pawn.class);
    when(botPawn.getPlayerNum()).thenReturn(botNum);
    when(cells[0][0].getPawn()).thenReturn(botPawn);

    int[] result = optimizer.getBestMoveForBot(botNum, destination, destinationZoneNum);

    assertNotNull(result);
    assertEquals(0, result[0]);
    assertEquals(0, result[1]);
    assertTrue(result[2] >= 0 && result[2] < 5, "Bot should stay within board boundaries.");
    assertTrue(result[3] >= 0 && result[3] < 5, "Bot should stay within board boundaries.");
  }

  @Test
  void testBotAvoidsOccupiedCells() {
    int botNum = 1;
    int[] destination = {4, 4};
    int destinationZoneNum = 1;

    // Place bot on the board
    when(cells[2][2].isOccupied()).thenReturn(true);
    Pawn botPawn = mock(Pawn.class);
    when(botPawn.getPlayerNum()).thenReturn(botNum);
    when(cells[2][2].getPawn()).thenReturn(botPawn);

    // Mark adjacent cells as occupied
    when(cells[3][2].isOccupied()).thenReturn(true);
    Pawn otherPawn1 = mock(Pawn.class);
    when(otherPawn1.getPlayerNum()).thenReturn(2);
    when(cells[3][2].getPawn()).thenReturn(otherPawn1);

    when(cells[2][3].isOccupied()).thenReturn(true);
    Pawn otherPawn2 = mock(Pawn.class);
    when(otherPawn2.getPlayerNum()).thenReturn(2);
    when(cells[2][3].getPawn()).thenReturn(otherPawn2);

    // Make the diagonal cell empty
    when(cells[3][3].isOccupied()).thenReturn(false);

    int[] result = optimizer.getBestMoveForBot(botNum, destination, destinationZoneNum);

    assertNotNull(result);
    assertEquals(2, result[0]); // Starting row
    assertEquals(2, result[1]); // Starting column
    assertEquals(3, result[2]); // Target row
    assertEquals(3, result[3]); // Target column
  }

}