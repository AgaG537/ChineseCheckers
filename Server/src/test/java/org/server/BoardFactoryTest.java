package org.server;

import org.junit.jupiter.api.Test;
import org.server.board.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BoardFactory class to ensure correct creation of different board variants.
 */
class BoardFactoryTest {

  /**
   * Tests the creation of a standard board variant.
   */
  @Test
  void testCreateStandardBoard() {
    Board board = BoardFactory.createBoard(10, 2, "standard");
    assertNotNull(board, "Standard Board should not be null");
    assertTrue(board instanceof BoardManager, "Standard Board should be an instance of BoardManager");
  }

  /**
   * Tests the creation of an order board variant.
   */
  @Test
  void testCreateOrderBoard() {
    Board board = BoardFactory.createBoard(10, 3, "order");
    assertNotNull(board, "Order Board should not be null");
    assertTrue(board instanceof OrderBoardManager, "Order Board should be an instance of OrderBoardManager");
  }

  /**
   * Tests the creation of a YinYang board variant.
   */
  @Test
  void testCreateYinYangBoard() {
    Board board = BoardFactory.createBoard(15, 2, "yinyang");
    assertNotNull(board, "YinYang Board should not be null");
    assertTrue(board instanceof YinYangBoardManager, "YinYang Board should be an instance of YinYangBoardManager");
  }

  /**
   * Tests that creating a board with an invalid variant returns null.
   */
  @Test
  void testInvalidVariantReturnsNull() {
    Board board = BoardFactory.createBoard(10, 2, "invalid_variant");
    assertNull(board, "Invalid variant should return null");
  }
}
