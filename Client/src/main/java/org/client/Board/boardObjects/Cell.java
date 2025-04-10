package org.client.Board.boardObjects;

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

  /**
   * Returns the flag of the cell.
   *
   * @return The flag of the cell.
   */
  public int getFlag() {
    return flag;
  }

  /**
   * Returns the row index of the cell.
   *
   * @return The row index of the cell.
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column index of the cell.
   *
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
   * Returns the player number initially assigned to the cell (0 if unoccupied).
   *
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
   * Returns the initial color of the cell.
   *
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
    updateCircle();
  }

  /**
   * Returns the current color of the cell (represents pawn).
   *
   * @return The current color of the cell (represents pawn).
   */
  public Color getCurrentColor() {
    return currentColor;
  }

  /**
   * Returns the pawn currently occupying the cell.
   *
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
   * Moves a pawn out of the cell, marking it as unoccupied and clearing its current color.
   */
  public void pawnMoveOut() {
    this.pawn = null;
    this.occupied = false;
    this.currentColor = Color.TRANSPARENT;
    updateCircle();
  }


  /**
   * Updates the Circle to reflect the current color of the cell and zone.
   */
  public void updateCircle() {
    this.setFill(currentColor);
    this.setStroke(this.zoneColor);
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

