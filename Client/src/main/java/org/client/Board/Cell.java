package org.client.Board;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a single cell on the game board.
 * Tracks the state, position, and ownership of the cell.
 */
public class Cell {
  private final int row;
  private final int col;
  private boolean insideBoard;
  private boolean occupied;
  private int initialPlayerNum;
  private Color initialColor;
  //private int currentPlayerNum;
  private Color currentColor;
  private Pawn pawn;
  private Circle circle;

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
    initialColor = currentColor = Color.TRANSPARENT;
    occupied = false;
    pawn = null;
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
//  public int getCurrentPlayerNum() {
//    return currentPlayerNum;
//  }

  /**
   * Updates the current player number for the cell.
   *
   * @param playerNum The new player number.
   */
//  public void setCurrentPlayerNum(int playerNum) {
//    currentPlayerNum = playerNum;
//  }

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
  }

  /**
   * Removes the pawn from the cell, marking it as unoccupied.
   *
   * @param pawn The pawn to remove from the cell.
   */
  public void pawnMoveOut(Pawn pawn) {
    this.pawn = null;
    this.occupied = false;
    this.currentColor = Color.TRANSPARENT;
  }

  /**
   * Sets the Circle representing the cell.
   * Updates the style and cursor.
   *
   * @param circle The Circle to associate with the cell.
   */
  public void setCircle(Circle circle) {
    this.circle = circle;
    circle.setStyle("-fx-stroke-width: 2");
    circle.setCursor(Cursor.HAND);
    circle.setStroke(initialColor);
    updateCircle();
  }

  /**
   * Updates the Circle to reflect the current color of the cell.
   */
  public void updateCircle() {
    circle.setFill(currentColor);
  }
}

