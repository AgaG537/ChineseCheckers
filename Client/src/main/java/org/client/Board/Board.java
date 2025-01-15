package org.client.Board;

/**
 * Interface representing a generic game board with methods for handling commands,
 * accessing board properties, and managing game state.
 */
public interface Board {

  /**
   * Handles a command to update the state of the board.
   *
   * @param command The command string containing board update instructions.
   */
  void handleCommand(String command);

  /**
   * Retrieves the constraint size for the board, used for validating game rules.
   *
   * @return The constraint size.
   */
  int getConstraintSize();

  /**
   * Gets the height of the board.
   *
   * @return The height of the board.
   */
  int getBoardHeight();

  /**
   * Gets the width of the board.
   *
   * @return The width of the board.
   */
  int getBoardWidth();

  /**
   * Retrieves the cell at the specified coordinates.
   *
   * @param x The x-coordinate of the cell.
   * @param y The y-coordinate of the cell.
   * @return The cell at the specified coordinates.
   */
  Cell getCell(int x, int y);

  /**
   * Sets the win state for the board.
   */
  void setWin();
}
