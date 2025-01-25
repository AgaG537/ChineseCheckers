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

  public void setMoveNumber(int moveNumber) {
    this.moveNumber = moveNumber;
  }
}
