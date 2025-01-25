package org.server.board.moveManagement;

/**
 * Interface with methods for validating and executing moves in the game.
 */
public interface IMoveValidator {

  /**
   * Makes a move on the board based on the provided input string.
   *
   * @param input The move details encoded as a string (e.g., "rowStart colStart rowEnd colEnd").
   * @return A string representing the executed move or a command string describing the result.
   */
  String makeMove(String input);

  /**
   * Validates a move based on the current game state.
   *
   * @param userId The ID of the player making the move.
   * @param input  The move details encoded as a string (e.g., "startRow startCol endRow endCol").
   * @return True if the move is valid; false otherwise.
   */
  boolean validateMove(int userId, String input);
}
