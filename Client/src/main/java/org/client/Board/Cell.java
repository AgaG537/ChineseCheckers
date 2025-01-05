package org.client.Board;

/**
 * Represents a single cell on the game board.
 */
public class Cell {
  private final int row;
  private final int col;
  private boolean insideBoard;
  private int userNum;

  /**
   * Constructor for the Cell class.
   *
   * @param row The row index of the cell
   * @param col The column index of the cell
   */
  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
    this.insideBoard = false;
    this.userNum = 0;
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
  public int getUserNum() {
    return userNum;
  }

  /**
   * Assigns a player number to the cell.
   *
   * @param userNum The player number to assign.
   */
  public void setUserNum(int userNum) {
    this.userNum = userNum;
  }

}

