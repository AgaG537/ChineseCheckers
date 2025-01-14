package org.client;

import javafx.scene.paint.Color;
import org.client.Board.Board;
import org.client.Board.BoardFactory;
import org.client.Board.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractBoardTest {

  private Board standardBoard;
  private Board orderBoard;
  private Board yinYangBoard;

  @BeforeEach
  void setUp() {
    int marblesPerPlayer = 10;
    int numOfPlayers = 4;

    standardBoard = BoardFactory.createBoard("standard", marblesPerPlayer, numOfPlayers);
    orderBoard = BoardFactory.createBoard("order", marblesPerPlayer, numOfPlayers);
    yinYangBoard = BoardFactory.createBoard("yinyang", marblesPerPlayer, 0);
  }

  @Test
  void testStandardBoardInitialization() {
    assertNotNull(standardBoard, "StandardBoard should not be null");
    assertTrue(standardBoard.getBoardWidth() > 0, "StandardBoard width should be greater than 0");
    assertTrue(standardBoard.getBoardHeight() > 0, "StandardBoard height should be greater than 0");

    Cell cell = standardBoard.getCell(0, 0);
    assertNotNull(cell, "Cells should be initialized");
  }

  @Test
  void testOrderBoardInitialization() {
    assertNotNull(orderBoard, "OrderBoard should not be null");
    assertTrue(orderBoard.getBoardWidth() > 0, "OrderBoard width should be greater than 0");
    assertTrue(orderBoard.getBoardHeight() > 0, "OrderBoard height should be greater than 0");
  }

  @Test
  void testYinYangBoardInitialization() {
    assertNotNull(yinYangBoard, "YinYangBoard should not be null");
    assertTrue(yinYangBoard.getBoardWidth() > 0, "YinYangBoard width should be greater than 0");
    assertTrue(yinYangBoard.getBoardHeight() > 0, "YinYangBoard height should be greater than 0");
  }

  @Test
  void testPlayerZoneSetup() {
    int numOfPlayers = 4;

    // Standard board player zones
    standardBoard = BoardFactory.createBoard("standard", 10, numOfPlayers);
    assertTrue(playerZonesSetUpCorrectly(standardBoard, numOfPlayers), "Player zones should be set up correctly for StandardBoard");

    // Order board player zones
    orderBoard = BoardFactory.createBoard("order", 10, numOfPlayers);
    assertTrue(playerZonesSetUpCorrectly(orderBoard, numOfPlayers), "Player zones should be set up correctly for OrderBoard");
  }

  private boolean playerZonesSetUpCorrectly(Board board, int numOfPlayers) {
    int zonesAssigned = 0;

    for (int i = 0; i < board.getBoardHeight(); i++) {
      for (int j = 0; j < board.getBoardWidth(); j++) {
        if (board.getCell(i, j).getInitialPlayerNum() > 0) {
          zonesAssigned++;
        }
      }
    }
    return zonesAssigned > 0;
  }

  @Test
  void testHandleCommand() {
    standardBoard.handleCommand("MOVE 4 4 5 5");

    Cell startCell = standardBoard.getCell(4, 4);
    Cell endCell = standardBoard.getCell(5, 5);

    assertFalse(startCell.isOccupied(), "Start cell should be empty after move");
    assertTrue(endCell.isOccupied(), "End cell should be occupied after move");
  }

  @Test
  void testWinCondition() {
    yinYangBoard.setWin();

    // Ensure cells no longer have actions
    for (int i = 0; i < yinYangBoard.getBoardHeight(); i++) {
      for (int j = 0; j < yinYangBoard.getBoardWidth(); j++) {
        assertNull(yinYangBoard.getCell(i, j).getOnMouseClicked(), "All cells should have no mouse click actions after win");
      }
    }
  }
}
