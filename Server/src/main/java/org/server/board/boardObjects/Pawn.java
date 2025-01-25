package org.server.board.boardObjects;

/**
 * Represents a pawn in the game.
 * Tracks the player to which the pawn belongs and its current position.
 */
public class Pawn {
  private final int playerNum;
  private Cell currCell = null;

  /**
   * Constructs a pawn belonging to a specific player and places it in an initial cell.
   *
   * @param playerNum The number of the player to whom the pawn belongs.
   * @param initCell  The initial cell where the pawn is placed.
   */
  public Pawn(int playerNum, Cell initCell) {
    this.playerNum = playerNum;
    setCurrCell(initCell);
  }

  /**
   * Retrieves the player number.
   *
   * @return The player number.
   */
  public int getPlayerNum() {
    return playerNum;
  }

  /**
   * Retrieves the current cell.
   *
   * @return The current cell.
   */
  public Cell getCurrCell() {
    return currCell;
  }

  /**
   * Updates the current cell of the pawn and moves it to the specified cell.
   * Automatically removes the pawn from the previous cell and places it in the new one.
   *
   * @param currCell The new cell to which the pawn is moved.
   */
  public void setCurrCell(Cell currCell) {
    currCell.pawnMoveOut();
    this.currCell = currCell;
    currCell.pawnMoveIn(this);
  }

  /**
   * Returns a string representation of the pawn, including its player number.
   *
   * @return A string describing the pawn.
   */
  @Override
  public String toString() {
    return "Pawn: " + ", playerNum: " + playerNum;
  }

}
