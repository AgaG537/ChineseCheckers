package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Represents a single cell on the game board.
 * Tracks the state, position, and ownership of the cell.
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
   * @return True if the cell is inside
   * the playable area of the board.
   */
  public boolean isInsideBoard() {
    return insideBoard;
  }

  /**
   * Marks the cell as being inside
   * the playable area of the board.
   */
  public void setInsideBoard() {
    insideBoard = true;
  }

  /**
   * @return The player number initially
   * assigned to the cell (0 if unoccupied).
   */
  public int getInitialPlayerNum() {
    return initialPlayerNum;
  }

  /**
   * Assigns an initial player number to the cell.
   *
   * @param playerNum The player number to assign.
   */
  public void setInitialPlayerNum(int playerNum) {
    initialPlayerNum = playerNum;
  }


  /**
   * @return The current player number
   * assigned to the cell (0 if unoccupied).
   */
  public int getCurrentPlayerNum() {
    return currentPlayerNum;
  }

  /**
   * Updates the current player number for the cell.
   *
   * @param playerNum The new player number.
   */
  public void setCurrentPlayerNum(int playerNum) {
    currentPlayerNum = playerNum;
  }

  /**
   * @return The initial color of the cell.
   */
  public Color getInitialColor() {
    return initialColor;
  }

  /**
   * Sets the initial color of the cell.
   *
   * @param color The color to assign.
   */
  public void setInitialColor(Color color) {
    initialColor = color;
  }

  /**
   * @return The current color of the cell (represents pawn).
   */
  public Color getCurrentColor() {
    return currentColor;
  }

  /**
   * Updates the current color of the cell (represents pawn).
   *
   * @param color The new color to assign.
   */
  public void setCurrentColor(Color color) {
    currentColor = color;
  }

}

