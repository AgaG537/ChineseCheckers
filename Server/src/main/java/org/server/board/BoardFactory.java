package org.server.board;

/**
 * A factory class for creating board instances based on the specified game variant.
 */
public class BoardFactory {

  /**
   * Creates a board instance based on the specified number of marbles, players, and variant.
   *
   * @param marbles      The number of marbles to place on the board.
   * @param numOfPlayers The number of players in the game.
   * @param variant      The game variant (e.g., "standard", "order", "yinyang").
   * @return A {@link Board} instance corresponding to the specified variant, or null if the variant is invalid.
   */
  public static Board createBoard(int marbles, int numOfPlayers, String variant) {
    Board board;
    switch (variant) {
      case "standard":
        board = new BoardManager(marbles,numOfPlayers,variant); break;
      case "order":
        board = new OrderBoardManager(marbles,numOfPlayers,variant); break;
      case "yinyang":
        board = new YinYangBoardManager(marbles); break;
      default:
        board = null;
    }
    return board;
  }
}
