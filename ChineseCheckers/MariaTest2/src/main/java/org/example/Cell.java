package org.example;

public class Cell {
  private int row;
  private int col;
  private int playerNum;
  private int zoneNum;
  private boolean isOccupied;
  private String name;


//  private Pawn pawn;

  public Cell(int row, int col, int playerNum, int zoneNum) {
    this.row = row;
    this.col = col;
    this.playerNum = playerNum;
    this.zoneNum = zoneNum;
//    pawn = null;
    isOccupied = false;
  }

  public int getRow() {
    return row;
  }
  public int getCol() {
    return col;
  }
  public int getPlayerNum() {
    return playerNum;
  }
  public int getZoneNum() {
    return zoneNum;
  }
  public boolean isOccupied() {
    return isOccupied;
  }
//  public Pawn getPawn() {
//    return pawn;
//  }
//  public void pawnMoveIn(Pawn pawn) {
//    this.pawn = pawn;
//    isOccupied = true;
//  }
//  public void pawnMoveOut() {
//    isOccupied = false;
//    pawn = null;
//  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public void setPlayerNum(int playerNum) {
    this.playerNum = playerNum;
  }
}

