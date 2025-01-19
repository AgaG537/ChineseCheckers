package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Represents a pawn in the game.
 * A pawn is associated with a player and a specific color and occupies a cell on the board.
 */
public class Pawn {
  private final int playerNum;
  private final Color color;
  private Cell currCell = null;


  /**
   * Creates a new Pawn object.
   *
   * @param playerNum The number of the player associated with the pawn.
   * @param color The color of the pawn.
   * @param initCell The initial cell where the pawn is placed.
   */
  public Pawn(int playerNum, Color color, Cell initCell) {
    this.playerNum = playerNum;
    this.color = color;
    setCurrCell(initCell);
    currCell.pawnMoveIn(this);
  }

  /**
   * Retrieves the player number associated with the pawn.
   *
   * @return The player number.
   */
  public int getPlayerNum() {
    return playerNum;
  }


  /**
   * Retrieves the color of the pawn.
   *
   * @return The color of the pawn.
   */
  public Color getColor() {
    return color;
  }


  /**
   * Retrieves the current cell occupied by the pawn.
   *
   * @return The current cell of the pawn.
   */
  public Cell getCurrCell() {
    return currCell;
  }


  /**
   * Updates the current cell occupied by the pawn.
   * This method ensures the pawn moves out of the old cell and into the new one.
   *
   * @param currCell The new cell to occupy.
   */
  public void setCurrCell(Cell currCell) {
    currCell.pawnMoveOut();
    this.currCell = currCell;
    currCell.pawnMoveIn(this);
  }

  /**
   * Provides a string representation of the pawn.
   *
   * @return A string containing the pawn's color and associated player number.
   */
  @Override
  public String toString() {
    return "Pawn, color: " + color.toString() + ", playerNum: " + playerNum;
  }

}
