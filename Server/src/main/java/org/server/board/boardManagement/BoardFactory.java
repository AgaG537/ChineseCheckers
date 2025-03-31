package org.server.board.boardManagement;

/**
 * A factory class for creating board instances based on the specified game variant.
 * This class allows the creation of different types of boards (e.g., standard, order, or Yin-Yang),
 * with the appropriate setup for each game variant.
 */
public class BoardFactory {

  /**
   * Creates a board instance based on the specified number of marbles, players, and variant.
   *
   * @param marbles      The number of marbles to place on the board.
   * @param numOfPlayers The number of players in the game.
   * @param variant      The game variant (e.g., "standard", "order", "yinyang").
   * @return A {@link Board} instance corresponding to the specified variant,
   *     or null if the variant is invalid.
   */
  public static Board createBoard(int marbles, int numOfPlayers, String variant, int seed) {
    return switch (variant) {
      case "order" -> new OrderBoardManager(marbles, numOfPlayers, seed);
      case "yinyang" -> new YinYangBoardManager(marbles, seed);
      default ->  new BoardManager(marbles, numOfPlayers, seed);
    };
  }
}
