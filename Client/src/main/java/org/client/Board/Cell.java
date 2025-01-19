package org.client.Board;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a single cell on the game board.
 * Tracks the state, position, and ownership of the cell.
 */
public class Cell extends Circle {
  private final int row;
  private final int col;
  private boolean insideBoard;
  private boolean occupied;
  private int initialPlayerNum;
  private Color zoneColor;
  private Color currentColor;
  private Pawn pawn;
  private int flag;

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
    initialPlayerNum = 0;
    zoneColor = currentColor = Color.TRANSPARENT;
    occupied = false;
    pawn = null;
    this.setRadius(0);
    flag = 0;
  }

  public int getFlag() {
    return flag;
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
   * Checks if the cell is inside board.
   *
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
   * @return The player number initially assigned to the cell (0 if unoccupied).
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
   * @return The initial color of the cell.
   */
  public Color getZoneColor() {
    return zoneColor;
  }

  /**
   * Sets the initial color of the cell.
   *
   * @param color The color to assign.
   */
  public void setZoneColor(Color color) {
    zoneColor = color;
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

  /**
   * @return The pawn currently occupying the cell.
   */
  public Pawn getPawn() {
    return pawn;
  }

  /**
   * Moves a pawn into the cell, marking it as occupied.
   *
   * @param pawn The pawn to place in the cell.
   */
  public void pawnMoveIn(Pawn pawn) {
    this.pawn = pawn;
    this.occupied = true;
    this.currentColor = pawn.getColor();
    updateCircle();
  }

  /**
   * Removes the pawn from the cell, marking it as unoccupied.
   *
   */
  public void pawnMoveOut() {
    this.pawn = null;
    this.occupied = false;
    this.currentColor = Color.TRANSPARENT;
    updateCircle();
  }


  /**
   * Updates the Circle to reflect the current color of the cell.
   */
  public void updateCircle() {
    this.setFill(currentColor);
  }

  /**
   * Checks whether the cell is occupied by a pawn.
   *
   * @return {@code true} if the cell is occupied; {@code false} otherwise.
   */
  public boolean isOccupied() {
    return occupied;
  }

  /**
   * Sets the flag for the cell.
   *
   * @param i The flag value to set.
   */
  public void setFlag(int i) {
    this.flag = i;
  }

}

