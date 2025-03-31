package org.server.board.boardObjects;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single cell on the game board.
 * Tracks its position, whether it is within the playable area,
 * and its current state (e.g., occupied or unoccupied).
 */
public class Cell {
  private final int row;
  private final int col;
  private boolean insideBoard;
  private boolean occupied;
  private int initialPlayerNum;
  private int zoneNum;
  private Pawn pawn;
  private List<Cell> neighbors;

  /**
   * Constructs a cell with specified row and column indices.
   * Initializes the cell's state as outside the playable area and unoccupied.
   *
   * @param row The row index of the cell.
   * @param col The column index of the cell.
   */
  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
    insideBoard = false;
    initialPlayerNum = 0;
    zoneNum = 0;
    occupied = false;
    pawn = null;
    neighbors = new ArrayList<>();
  }

  /**
   * Sets the neighboring cells for this cell.
   *
   * @param neighbors A list of neighboring cells.
   */
  public void setNeighbors(List<Cell> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * Retrieves the list of neighboring cells.
   *
   * @return A list of neighboring cells.
   */
  public List<Cell> getNeighbors() {
    return neighbors;
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
   * Returns true if the cell is inside the playable area of the board.
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
   * Returns the zone number initially assigned to the cell (0 if unoccupied).
   *
   * @return The zone number initially assigned to the cell (0 if unoccupied).
   */
  public int getZoneNum() {
    return zoneNum;
  }

  /**
   * Assigns a zone number to the cell.
   *
   * @param zoneNum The zone number to assign.
   */
  public void setZoneNum(int zoneNum) {
    this.zoneNum = zoneNum;
  }


  /**
   * Retrieves the pawn currently occupying the cell.
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
  }

  /**
   * Removes the pawn from the cell, marking it as unoccupied.
   */
  public void pawnMoveOut() {
    this.pawn = null;
    this.occupied = false;
  }

  /**
   * Checks whether the cell is currently occupied by a pawn.
   *
   * @return True if the cell is occupied; false otherwise.
   */
  public boolean isOccupied() {
    return occupied;
  }

}
