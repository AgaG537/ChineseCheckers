package org.server;

import org.junit.jupiter.api.Test;
import org.server.board.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

  @Test
  void testCreateStandardBoard() {
    Board board = BoardFactory.createBoard(10, 2, "standard");
    assertNotNull(board, "Standard Board should not be null");
    assertTrue(board instanceof BoardManager, "Standard Board should be an instance of BoardManager");
  }

  @Test
  void testCreateOrderBoard() {
    Board board = BoardFactory.createBoard(10, 3, "order");
    assertNotNull(board, "Order Board should not be null");
    assertTrue(board instanceof OrderBoardManager, "Order Board should be an instance of OrderBoardManager");
  }

  @Test
  void testCreateYinYangBoard() {
    Board board = BoardFactory.createBoard(15, 2, "yinyang");
    assertNotNull(board, "YinYang Board should not be null");
    assertTrue(board instanceof YinYangBoardManager, "YinYang Board should be an instance of YinYangBoardManager");
  }

  @Test
  void testInvalidVariantReturnsNull() {
    Board board = BoardFactory.createBoard(10, 2, "invalid_variant");
    assertNull(board, "Invalid variant should return null");
  }
}
