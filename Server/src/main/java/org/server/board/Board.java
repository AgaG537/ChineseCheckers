package org.server.board;

/**
 * Interface for game board managers, defining core functionalities like move-making,
 * validation, and win condition checks.
 */
public interface Board {

  int checkWinCondition();

  String makeCreate(int row, int col);

  Cell[][] getCells();

}
