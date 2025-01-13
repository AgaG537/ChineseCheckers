package org.client.Board;

public class BoardFactory {
  public static Board createBoard(String variant,int marblesPerPlayer, int numOfPlayers) {
    Board board;
    switch (variant) {
      case "standard":
        board = new StandardBoard(marblesPerPlayer, numOfPlayers); break;
      case "capture":
        board = new CaptureBoard(marblesPerPlayer,numOfPlayers); break;
      default:
        board = null;
    }
    return board;
  }
}
