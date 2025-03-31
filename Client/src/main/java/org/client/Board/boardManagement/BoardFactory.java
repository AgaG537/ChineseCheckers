package org.client.Board.boardManagement;


/**
 * Generates a game board based on the specified type and configurations.
 * Supports multiple board variants.
 */
public class BoardFactory {

  /**
   * Creates a board based on the specified variant, marbles per player, and number of players.
   *
   * @param variant The type of board (e.g., "standard", "order", "yinyang").
   * @param marblesPerPlayer Number of marbles each player has.
   * @param numOfPlayers Number of players in the game.
   * @return The created board object.
   */
  public static Board createBoard(String variant, int marblesPerPlayer, int numOfPlayers) {
    return switch (variant) {
      case "order" -> new OrderBoard(marblesPerPlayer, numOfPlayers);
      case "yinyang" -> new YinYangBoard(marblesPerPlayer);
      default -> new StandardBoard(marblesPerPlayer, numOfPlayers);
    };
  }
}
