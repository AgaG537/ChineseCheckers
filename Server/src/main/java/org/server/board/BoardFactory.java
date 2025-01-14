package org.server.board;

public class BoardFactory {
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
