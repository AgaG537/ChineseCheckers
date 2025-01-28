package org.server.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MoveRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int moveNumber;
  private int gameNumber;
  private int cellRowNumber;
  private int cellColumnNumber;
  private int cellPlayerNumber;
  private int cellZoneNumber;
  private String variant;
  private int numOfPlayers;
  private int numOfBots;

  public MoveRecord() {}

  public MoveRecord(int moveNumber) {
    this.moveNumber = moveNumber;
  }

  public Long getId() {
    return id;
  }

  public int getMoveNumber() {
    return moveNumber;
  }

  public int getNumOfBots() {
    return numOfBots;
  }
  public void setNumOfBots(int numOfBots) {
    this.numOfBots = numOfBots;
  }

  public void setMoveNumber(int moveNumber) {
    this.moveNumber = moveNumber;
  }

  public int getGameNumber() {
    return gameNumber;
  }
  public void setGameNumber(int gameNumber) {
    this.gameNumber = gameNumber;
  }
  public int getCellRowNumber() {
    return cellRowNumber;
  }
  public void setCellRowNumber(int cellRowNumber) {
    this.cellRowNumber = cellRowNumber;
  }
  public int getCellColumnNumber() {
    return cellColumnNumber;
  }
  public void setCellColumnNumber(int cellColumnNumber) {
    this.cellColumnNumber = cellColumnNumber;
  }
  public int getCellPlayerNumber() {
    return cellPlayerNumber;
  }
  public void setCellPlayerNumber(int cellPlayerNumber) {
    this.cellPlayerNumber = cellPlayerNumber;
  }
  public int getCellZoneNumber() {
    return cellZoneNumber;
  }
  public void setCellZoneNumber(int cellZoneNumber) {
    this.cellZoneNumber = cellZoneNumber;
  }
  public String getVariant() {
    return variant;
  }
  public void setVariant(String variant) {
    this.variant = variant;
  }
  public int getNumOfPlayers() {
    return numOfPlayers;
  }
  public void setNumOfPlayers(int numOfPlayers) {
    this.numOfPlayers = numOfPlayers;
  }
}
