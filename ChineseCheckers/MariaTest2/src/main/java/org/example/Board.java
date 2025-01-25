package org.example;

import java.util.Random;

public class Board {
  public int turn;
  public int numPlayers;
  public Cell[][] cells;
  public Board(int height, int width, int numPlayers) {
    cells = new Cell[height][width];
    initializeCells();
    turn = 0;
    this.numPlayers = numPlayers;
  }
  private void initializeCells() {
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        cells[i][j] = new Cell(i,j,i,i+1);
      }
    }
  }

  public Cell getCell(int x, int y) {
    return cells[x][y];
  }

  public void singleTurn() {
    Random rand = new Random();
    for (Cell[] cellRow : cells) {
      for (Cell cell : cellRow) {
        cell.setPlayerNum(rand.nextInt(numPlayers));
      }
    }
    turn++;
    if (turn == numPlayers) {
      turn = 0;
    }
  }
}
