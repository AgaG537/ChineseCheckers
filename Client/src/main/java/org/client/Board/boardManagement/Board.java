package org.client.Board.boardManagement;

import javafx.scene.paint.Color;
import org.client.Board.boardObjects.Cell;

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

  /**
   * Handles a command to create game elements such as pawns or zones on the board.
   *
   * @param command The command string specifying creation instructions.
   */
  void handleCreate(String command);

  /**
   * Configures the cell with the pawn's properties.
   *
   * @param playerNum The number of the player owning the pawn.
   * @param color     The color associated with the pawn.
   * @param cell      The cell where the pawn is to be placed.
   */
  void setupPawn(int playerNum, Color color, Cell cell);
}
