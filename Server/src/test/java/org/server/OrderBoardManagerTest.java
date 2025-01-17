//package org.server;
//
//import org.junit.jupiter.api.Test;
//import org.server.board.OrderBoardManager;
//import org.server.board.Pawn;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Unit tests for the OrderBoardManager class, specifically validating order-specific rules and zone setup.
// */
//class OrderBoardManagerTest {
//
//  /**
//   * Verifies that player zones are correctly set up during initialization.
//   */
//  @Test
//  void testSetupPlayerZones() {
//    int marbles = 10;
//    int numOfPlayers = 4;
//
//    OrderBoardManager boardManager = new OrderBoardManager(marbles, numOfPlayers);
//
//    // Verify player zones are set up
//    int playerZoneCells = 0;
//    for (int i = 0; i < boardManager.getBoardHeight(); i++) {
//      for (int j = 0; j < boardManager.getBoardWidth(); j++) {
//        if (boardManager.getCell(i, j).getZoneNum() > 0) {
//          playerZoneCells++;
//        }
//      }
//    }
//
//    assertTrue(playerZoneCells > 0, "Player zones should be set up");
//  }
//
//  /**
//   * Tests move validation specific to the order board rules.
//   */
//  @Test
//  void testMakeMoveInOrderBoard() {
//    OrderBoardManager boardManager = new OrderBoardManager(10, 2);
//
//    // Place a pawn on the board for player 1
//    boardManager.getCell(5, 5).pawnMoveIn(new Pawn(1,boardManager.getCell(5,5)));
//
//    String moveInput = "5 5 1 7 7 0";
//    boolean moveValid = boardManager.validateMove(1, moveInput);
//
//    assertFalse(moveValid, "Move should not be valid as it doesn't follow the rules");
//  }
//}
