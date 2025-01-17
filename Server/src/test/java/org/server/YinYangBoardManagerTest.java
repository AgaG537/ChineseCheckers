//package org.server;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.server.board.Pawn;
//import org.server.board.YinYangBoardManager;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Unit tests for the YinYangBoardManager class, ensuring compliance with YinYang-specific game rules and mechanics.
// */
//class YinYangBoardManagerTest {
//
//  private YinYangBoardManager yinYangBoardManager;
//
//  /**
//   * Initializes a new YinYang board manager before each test.
//   */
//  @BeforeEach
//  void setUp() {
//    yinYangBoardManager = new YinYangBoardManager(10);
//  }
//
//  /**
//   * Verifies the initialization of the YinYang board, including dimensions.
//   */
//  @Test
//  void testBoardInitialization() {
//    // Check that the board is not null
//    assertNotNull(yinYangBoardManager, "YinYangBoardManager instance should not be null");
//
//    // Verify dimensions
//    int boardHeight = yinYangBoardManager.getBoardHeight();
//    int boardWidth = yinYangBoardManager.getBoardWidth();
//    assertTrue(boardHeight > 0, "Board height should be greater than 0");
//    assertTrue(boardWidth > 0, "Board width should be greater than 0");
//  }
//
//  /**
//   * Validates moves according to YinYang-specific rules, including valid and invalid cases.
//   */
//  @Test
//  void testValidateMoveForYinYang() {
//    // Place a pawn for player 1
//    yinYangBoardManager.getCell(4, 4).pawnMoveIn(new Pawn(1, yinYangBoardManager.getCell(4,4)));
//
//    // Valid move: within board and aligns with game rules
//    String validMoveInput = "4 4 1 3 3 0";
//    boolean isValidMove = yinYangBoardManager.validateMove(1, validMoveInput);
//    assertTrue(isValidMove, "Move should be valid according to YinYang rules");
//
//
//    // Invalid move: wrong player
//    String invalidMoveInputWrongPlayer = "4 4 1 5 5 0";
//    boolean isInvalidMoveWrongPlayer = yinYangBoardManager.validateMove(2, invalidMoveInputWrongPlayer);
//    assertFalse(isInvalidMoveWrongPlayer, "Move should be invalid for the wrong player");
//  }
//
//
//}
