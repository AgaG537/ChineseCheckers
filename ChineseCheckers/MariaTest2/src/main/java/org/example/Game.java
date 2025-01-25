package org.example;

import jakarta.persistence.*;

@Entity
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int gameNum;
  private int moveNum;
  private int cellRow;
  private int cellCol;
  private int cellPlayerNum;
  private int cellZoneNum;
  private String cellName;

//  private String title;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
//  public String getTitle() {
//    return title;
//  }
//  public void setTitle(String title) {
//    this.title = title;
//  }
  public int getGameNum() {
    return gameNum;
  }
  public void setGameNum(int gameNum) {
    this.gameNum = gameNum;
  }
  public int getMoveNum() {
    return moveNum;
  }
  public void setMoveNum(int moveNum) {
    this.moveNum = moveNum;
  }
  public int getCellRow() {
    return cellRow;
  }
  public void setCellRow(int cellRow) {
    this.cellRow = cellRow;
  }
  public int getCellCol() {
    return cellCol;
  }
  public void setCellCol(int cellCol) {
    this.cellCol = cellCol;
  }
  public int getCellPlayerNum() {
    return cellPlayerNum;
  }
  public void setCellPlayerNum(int cellPlayerNum) {
    this.cellPlayerNum = cellPlayerNum;
  }
  public int getCellZoneNum() {
    return cellZoneNum;
  }
  public void setCellZoneNum(int cellZoneNum) {
    this.cellZoneNum = cellZoneNum;
  }
  public String getCellName() {
    return cellName;
  }
  public void setCellName(String cellName) {
    this.cellName = cellName;
  }
}
