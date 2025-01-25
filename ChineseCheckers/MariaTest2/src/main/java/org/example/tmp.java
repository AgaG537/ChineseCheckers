package org.example;

public class tmp {
  public static void main(String[] args) {
    Board board = new Board(2,2,2);

    for (Cell[] cellRow : board.cells) {
      for (Cell cell : cellRow) {
        System.out.printf("row: %d, col: %d\n", cell.getRow(), cell.getCol());
        System.out.printf("player num: %d\n", cell.getPlayerNum());
        System.out.printf("zoneNum: %d\n", cell.getZoneNum());
      }
    }

    System.out.println("\n\n\n\n\n\n\n\n\n\n\n");

    board.singleTurn();
    int counter = 0;
    System.out.printf("turn: %d\n", board.turn);
    for (Cell[] cellRow : board.cells) {
      for (Cell cell : cellRow) {
        System.out.printf("counter: %d\n", counter);
        System.out.printf("row: %d, col: %d\n", cell.getRow(), cell.getCol());
        System.out.printf("player num: %d\n", cell.getPlayerNum());
        System.out.printf("zoneNum: %d\n\n", cell.getZoneNum());
        counter++;
      }
    }


    System.out.println("\n\n\n\n\n\n");

    board.singleTurn();
    counter = 0;
    System.out.printf("turn: %d\n", board.turn);
    for (Cell[] cellRow : board.cells) {
      for (Cell cell : cellRow) {
        System.out.printf("counter: %d\n", counter);
        System.out.printf("row: %d, col: %d\n", cell.getRow(), cell.getCol());
        System.out.printf("player num: %d\n", cell.getPlayerNum());
        System.out.printf("zoneNum: %d\n\n", cell.getZoneNum());
        counter++;
      }
    }
  }
}
