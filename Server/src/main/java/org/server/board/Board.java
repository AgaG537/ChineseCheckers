package org.server.board;

/**
 * Interface for game board managers, defining core functionalities such as making moves,
 * validating game progress, and checking win conditions.
 */
public interface Board {

  /**
   * Sets up the specified cell by assigning it to a zone and placing a pawn if necessary.
   * Depending on the active zone, the cell is either assigned to the default player or left empty.
   * If the cell belongs to an active zone, it is also assigned a pawn representing the player.
   *
   * @param cell The cell to be set up.
   * @param zoneNum The zone number where the cell belongs.
   * @param defaultPlayerNum The player number to be assigned to the cell if it is part of the active zone.
   * @param activeZoneNums An array indicating which zones are active (1 for active, 0 for inactive).
   */
  void setupCell(Cell cell, int zoneNum, int defaultPlayerNum, int[] activeZoneNums);

  /**
   * Checks the win condition for the game.
   *
   * @return The player number who wins, or 0 if no winner yet.
   */
  int checkWinCondition();

  /**
   * Creates a string representation for creating a pawn at a specific position on the board.
   *
   * @param row The row index where the pawn is to be created.
   * @param col The column index where the pawn is to be created.
   * @return A string with the pawn creation details.
   */
  String makeCreate(int row, int col);

  /**
   * Retrieves the 2D array of cells representing the board.
   *
   * @return A 2D array of Cell objects.
   */
  Cell[][] getCells();

}
