package org.server.board;

public interface IMoveValidator {

  /**
   * Makes a move on the board based on the input string.
   *
   * @param input The move details encoded as a string (e.g., "rowStart colStart rowEnd colEnd").
   * @return A command string describing the move made.
   */
  String makeMove(String input);

  /**
   * Validates the legality of a move based on the current board state.
   *
   * @param userId The player ID making the move.
   * @param input  The move details encoded as a string (e.g., "startRow startCol endRow endCol").
   * @return True if the move is valid; false otherwise.
   */
  boolean validateMove(int userId, String input);
}
