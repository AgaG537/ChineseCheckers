package org.client.Board;

import javafx.scene.paint.Color;

public class Pawn {
  private final int playerNum;
  private final Color color;
  private Cell currCell = null;


  public Pawn(int playerNum, Color color, Cell initCell) {
    this.playerNum = playerNum;
    this.color = color;
    setCurrCell(initCell);
    currCell.pawnMoveIn(this);
  }

  public int getPlayerNum() {
    return playerNum;
  }

  public Color getColor() {
    return color;
  }

  public Cell getCurrCell() {
    return currCell;
  }

  public void setCurrCell(Cell currCell) {
    currCell.pawnMoveOut();
    this.currCell = currCell;
    currCell.pawnMoveIn(this);
  }

  @Override
  public String toString() {
    return "Pawn, color: " + color.toString() + ", playerNum: " + playerNum;
  }

}
