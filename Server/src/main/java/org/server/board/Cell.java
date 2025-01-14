package org.server.board;


import java.util.ArrayList;
import java.util.List;

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
  private int zoneNum;
  private Pawn pawn;
  private List<Cell> neighbors;
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
    zoneNum = 0;
    occupied = false;
    pawn = null;
    neighbors = new ArrayList<Cell>();
  }

  public void setNeighbors(List<Cell> neighbors) {
    this.neighbors=neighbors;
  }

  public List<Cell> getNeighbors() {
    return neighbors;
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
   * @return The zone number initially
   * assigned to the cell (0 if unoccupied).
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
   *
   */
  public void pawnMoveOut() {
    this.pawn = null;
    this.occupied = false;
  }

  public boolean isOccupied() {
    return occupied;
  }

}
