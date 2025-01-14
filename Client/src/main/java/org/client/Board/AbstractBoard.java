package org.client.Board;

import javafx.scene.paint.Color;

public abstract class AbstractBoard implements Board {
  protected final int playerZoneHeight;
  protected final int boardHeight;
  protected final int boardWidth;
  protected final int constraintSize;
  protected Cell[][] cells;

  public AbstractBoard(int marblesPerPlayer, int numOfPlayers) {
    playerZoneHeight = calculatePlayerZoneHeight(marblesPerPlayer);
    boardHeight = playerZoneHeight * 4 + 1;
    boardWidth = playerZoneHeight * 6 + 1;
    constraintSize = (1000 / boardWidth) / 2;
    cells = new Cell[boardHeight][boardWidth];
    initializeBoard();
    setupPlayerZones(numOfPlayers);
  }

  protected abstract void setupPlayerZones(int numOfPlayers);

  private int calculatePlayerZoneHeight(int marblesPerPlayer) {
    int sum = 0;
    int heightCounter = 0;
    while (sum < marblesPerPlayer) {
      heightCounter++;
      sum += heightCounter;
    }
    return sum == marblesPerPlayer ? heightCounter : 0;
  }

  private void initializeBoard() {
    for (int i = 0; i < boardHeight; i++) {
      for (int j = 0; j < boardWidth; j++) {
        cells[i][j] = new Cell(i, j);
      }
    }
    markValidBoardPositions();
  }

  private void markValidBoardPositions() {
    int k = 0;
    for (int i = 0; i < boardHeight - playerZoneHeight; i++) {
      for (int j = (boardWidth / 2) - k; j <= (boardWidth / 2) + k; j += 2) {
        setCellAsInsideBoard(i, j);
        setCellAsInsideBoard(boardHeight - 1 - i, j);
      }
      k++;
    }
  }

  private void setCellAsInsideBoard(int row, int col) {
    if (!cells[row][col].isInsideBoard()) {
      cells[row][col].setInsideBoard();
      cells[row][col].setZoneColor(Color.web("#ffa64d"));
    }
  }

  @Override
  public int getConstraintSize() {
    return constraintSize;
  }

  @Override
  public int getBoardWidth() {
    return boardWidth;
  }

  @Override
  public int getBoardHeight() {
    return boardHeight;
  }

  @Override
  public Cell getCell(int row, int col) {
    return cells[row][col];
  }

  @Override
  public void handleCommand(String command) {
    int[] positions = decodeCommand(command);
    Pawn pawn = cells[positions[0]][positions[1]].getPawn();
    cells[positions[0]][positions[1]].pawnMoveOut();
    cells[positions[2]][positions[3]].pawnMoveIn(pawn);
  }

  private int[] decodeCommand(String command) {
    String[] tokens = command.split(" ");
    int[] result = new int[tokens.length - 1];
    try {
      for (int i = 1; i < tokens.length; i++) {
        result[i - 1] = Integer.parseInt(tokens[i]);
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void setWin() {
    for (int i = 0; i < boardHeight; i++) {
      for (int j = 0; j < boardWidth; j++) {
        cells[i][j].setOnMouseClicked(null);
      }
    }
  }
}
