package org.client.Board;

public class BoardFactory {
  public static Board createBoard(String variant,int marblesPerPlayer, int numOfPlayers) {
    Board board;
    switch (variant) {
      case "standard":
        board = new StandardBoard(marblesPerPlayer, numOfPlayers); break;
      case "order":
        board = new OrderBoard(marblesPerPlayer,numOfPlayers); break;
      case "yinyang":
        board = new YinYangBoard(marblesPerPlayer); break;
      default:
        board = null;
    }
    return board;
  }
}
