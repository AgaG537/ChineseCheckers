package org.server.board;

public class Pawn {
  private int playerNum;
  private Cell currCell = null;


  public Pawn(int playerNum, Cell initCell) {
    this.playerNum = playerNum;
    setCurrCell(initCell);
  }

  public int getPlayerNum() {
    return playerNum;
  }


  public Cell getCurrCell() {
    return currCell;
  }

  public void setCurrCell(Cell currCell) {
    currCell.pawnMoveOut(this);
    this.currCell = currCell;
    currCell.pawnMoveIn(this);
  }

  @Override
  public String toString() {
    return "Pawn: " + ", playerNum: " + playerNum;
  }

}
