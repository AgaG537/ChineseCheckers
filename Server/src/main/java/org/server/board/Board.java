package org.server.board;

/**
 * Interface for game board managers, defining core functionalities like move-making,
 * validation, and win condition checks.
 */
public interface Board {

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
  boolean validateMove(int userId,String input);

  /**
   * Checks for a winning condition on the board.
   *
   * @return The player number of the winner, or 0 if no winner is determined.
   */
  int checkWin();

  public String makeCreate(int row, int col);
  public Cell[][] getCells();

}
