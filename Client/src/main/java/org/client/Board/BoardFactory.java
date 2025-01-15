package org.client.Board;

import org.client.Client;

/**
 * Factory class for creating game boards of different variants.
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
    Board board;
    switch (variant) {
      case "standard":
        board = new StandardBoard(marblesPerPlayer, numOfPlayers);
        break;
      case "order":
        board = new OrderBoard(marblesPerPlayer, numOfPlayers);
        break;
      case "yinyang":
        board = new YinYangBoard(marblesPerPlayer);
        break;
      default:
        board = null;
    }
    return board;
  }
}
