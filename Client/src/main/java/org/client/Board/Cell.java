package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Represents a single cell on the game board.
 */
public class Cell {
  private final int row;
  private final int col;
  private boolean insideBoard;
  private int initialPlayerNum;
  private Color initialColor;
  private int currentPlayerNum;
  private Color currentColor;

  /**
   * Constructor for the Cell class.
   *
   * @param row The row index of the cell
   * @param col The column index of the cell
   */
  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
    insideBoard = false;
    initialPlayerNum = currentPlayerNum = 0;
    initialColor = currentColor = Color.TRANSPARENT;
  }

  /**
   * @return The row index of the cell.
   */
  public int getRow() {
    return row;
  }

  /**
   * @return The column index of the cell.
   */
  public int getCol() {
    return col;
  }

  /**
   * @return True if the cell is inside the playable area of the board.
   */
  public boolean isInsideBoard() {
    return insideBoard;
  }

  /**
   * Marks the cell as being inside the playable area of the board.
   */
  public void setInsideBoard() {
    insideBoard = true;
  }

  /**
   * @return The player number assigned to the cell (0 if unoccupied).
   */
  public int getInitialPlayerNum() {
    return initialPlayerNum;
  }

  /**
   * Assigns a player number to the cell.
   *
   * @param playerNum The player number to assign.
   */
  public void setInitialPlayerNum(int playerNum) {
    initialPlayerNum = playerNum;
  }


  /**
   * @return The player number assigned to the cell (0 if unoccupied).
   */
  public int getCurrentPlayerNum() {
    return currentPlayerNum;
  }

  /**
   * Assigns a player number to the cell.
   *
   * @param playerNum The player number to assign.
   */
  public void setCurrentPlayerNum(int playerNum) {
    currentPlayerNum = playerNum;
  }

  public Color getInitialColor() {
    return initialColor;
  }

  public void setInitialColor(Color color) {
    initialColor = color;
  }

  public Color getCurrentColor() {
    return currentColor;
  }

  public void setCurrentColor(Color color) {
    currentColor = color;
  }

}

