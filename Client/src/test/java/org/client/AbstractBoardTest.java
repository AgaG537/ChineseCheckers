//package org.client;
//
//import org.client.Board.Board;
//import org.client.Board.BoardFactory;
//import org.client.Board.Cell;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Unit tests for the AbstractBoard class and its subclasses.
// */
//class AbstractBoardTest {
//
//  private Board standardBoard;
//  private Board orderBoard;
//  private Board yinYangBoard;
//
//  /**
//   * Initializes a new board for given variant before each test.
//   */
//  @BeforeEach
//  void setUp() {
//    int marblesPerPlayer = 10;
//    int numOfPlayers = 4;
//
//    standardBoard = BoardFactory.createBoard("standard", marblesPerPlayer, numOfPlayers);
//    orderBoard = BoardFactory.createBoard("order", marblesPerPlayer, numOfPlayers);
//    yinYangBoard = BoardFactory.createBoard("yinyang", marblesPerPlayer, 0);
//  }
//
//  /**
//   * Tests initialization of a StandardBoard and ensures its properties are set correctly.
//   */
//  @Test
//  void testStandardBoardInitialization() {
//    assertNotNull(standardBoard, "StandardBoard should not be null");
//    assertTrue(standardBoard.getBoardWidth() > 0, "StandardBoard width should be greater than 0");
//    assertTrue(standardBoard.getBoardHeight() > 0, "StandardBoard height should be greater than 0");
//
//    Cell cell = standardBoard.getCell(0, 0);
//    assertNotNull(cell, "Cells should be initialized");
//  }
//
//  /**
//   * Tests initialization of an OrderBoard and ensures its properties are set correctly.
//   */
//  @Test
//  void testOrderBoardInitialization() {
//    assertNotNull(orderBoard, "OrderBoard should not be null");
//    assertTrue(orderBoard.getBoardWidth() > 0, "OrderBoard width should be greater than 0");
//    assertTrue(orderBoard.getBoardHeight() > 0, "OrderBoard height should be greater than 0");
//  }
//
//  /**
//   * Tests initialization of a YinYangBoard and ensures its properties are set correctly.
//   */
//  @Test
//  void testYinYangBoardInitialization() {
//    assertNotNull(yinYangBoard, "YinYangBoard should not be null");
//    assertTrue(yinYangBoard.getBoardWidth() > 0, "YinYangBoard width should be greater than 0");
//    assertTrue(yinYangBoard.getBoardHeight() > 0, "YinYangBoard height should be greater than 0");
//  }
//
//  /**
//   * Verifies that player zones are correctly set up for different board types.
//   */
//  @Test
//  void testPlayerZoneSetup() {
//    int numOfPlayers = 4;
//
//    // Standard board player zones
//    standardBoard = BoardFactory.createBoard("standard", 10, numOfPlayers);
//    assertTrue(playerZonesSetUpCorrectly(standardBoard), "Player zones should be set up correctly for StandardBoard");
//
//    // Order board player zones
//    orderBoard = BoardFactory.createBoard("order", 10, numOfPlayers);
//    assertTrue(playerZonesSetUpCorrectly(orderBoard), "Player zones should be set up correctly for OrderBoard");
//  }
//
//  /**
//   * Verifies if player zones are correctly set up on the board.
//   *
//   * @param board The board to check.
//   * @return {@code true} if at least one cell is assigned to a player zone.
//   */
//  private boolean playerZonesSetUpCorrectly(Board board) {
//    int zonesAssigned = 0;
//
//    for (int i = 0; i < board.getBoardHeight(); i++) {
//      for (int j = 0; j < board.getBoardWidth(); j++) {
//        if (board.getCell(i, j).getInitialPlayerNum() > 0) {
//          zonesAssigned++;
//        }
//      }
//    }
//    return zonesAssigned > 0;
//  }
//
//  /**
//   * Tests the functionality of the handleCommand method in AbstractBoard.
//   */
//  @Test
//  void testHandleCommand() {
//    standardBoard.handleCommand("MOVE 4 4 5 5");
//
//    Cell startCell = standardBoard.getCell(4, 4);
//    Cell endCell = standardBoard.getCell(5, 5);
//
//    assertFalse(startCell.isOccupied(), "Start cell should be empty after move");
//    assertTrue(endCell.isOccupied(), "End cell should be occupied after move");
//  }
//
//  /**
//   * Ensures that the win condition is properly applied and affects all cells.
//   */
//  @Test
//  void testWinCondition() {
//    yinYangBoard.setWin();
//
//    // Ensure cells no longer have actions
//    for (int i = 0; i < yinYangBoard.getBoardHeight(); i++) {
//      for (int j = 0; j < yinYangBoard.getBoardWidth(); j++) {
//        assertNull(yinYangBoard.getCell(i, j).getOnMouseClicked(), "All cells should have no mouse click actions after win");
//      }
//    }
//  }
//}
