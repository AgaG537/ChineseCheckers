package org.server.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a record of a move in a game.
 * This entity is stored in the database and is used to track the state of each move made during the game.
 */
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

  /**
   * Default constructor for MoveRecord.
   */
  public MoveRecord() {}

  /**
   * Constructor that initializes the move record with the given move number.
   *
   * @param moveNumber The move number for this record.
   */
  public MoveRecord(int moveNumber) {
    this.moveNumber = moveNumber;
  }

  /**
   * Returns the unique identifier for this move record.
   *
   * @return The ID of the move record.
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the move number associated with this record.
   *
   * @return The move number.
   */
  public int getMoveNumber() {
    return moveNumber;
  }

  /**
   * Returns the number of bots in the game for this move record.
   *
   * @return The number of bots.
   */
  public int getNumOfBots() {
    return numOfBots;
  }

  /**
   * Sets the number of bots for this move record.
   *
   * @param numOfBots The number of bots to set.
   */
  public void setNumOfBots(int numOfBots) {
    this.numOfBots = numOfBots;
  }

  /**
   * Sets the move number for this move record.
   *
   * @param moveNumber The move number to set.
   */
  public void setMoveNumber(int moveNumber) {
    this.moveNumber = moveNumber;
  }

  /**
   * Returns the game number associated with this move record.
   *
   * @return The game number.
   */
  public int getGameNumber() {
    return gameNumber;
  }

  /**
   * Sets the game number for this move record.
   *
   * @param gameNumber The game number to set.
   */
  public void setGameNumber(int gameNumber) {
    this.gameNumber = gameNumber;
  }

  /**
   * Returns the row number of the cell for this move.
   *
   * @return The cell's row number.
   */
  public int getCellRowNumber() {
    return cellRowNumber;
  }

  /**
   * Sets the row number of the cell for this move.
   *
   * @param cellRowNumber The row number to set.
   */
  public void setCellRowNumber(int cellRowNumber) {
    this.cellRowNumber = cellRowNumber;
  }

  /**
   * Returns the column number of the cell for this move.
   *
   * @return The cell's column number.
   */
  public int getCellColumnNumber() {
    return cellColumnNumber;
  }

  /**
   * Sets the column number of the cell for this move.
   *
   * @param cellColumnNumber The column number to set.
   */
  public void setCellColumnNumber(int cellColumnNumber) {
    this.cellColumnNumber = cellColumnNumber;
  }

  /**
   * Returns the player number associated with this move's cell.
   *
   * @return The player number.
   */
  public int getCellPlayerNumber() {
    return cellPlayerNumber;
  }

  /**
   * Sets the player number associated with this move's cell.
   *
   * @param cellPlayerNumber The player number to set.
   */
  public void setCellPlayerNumber(int cellPlayerNumber) {
    this.cellPlayerNumber = cellPlayerNumber;
  }

  /**
   * Returns the zone number of the cell for this move.
   *
   * @return The cell's zone number.
   */
  public int getCellZoneNumber() {
    return cellZoneNumber;
  }

  /**
   * Sets the zone number of the cell for this move.
   *
   * @param cellZoneNumber The zone number to set.
   */
  public void setCellZoneNumber(int cellZoneNumber) {
    this.cellZoneNumber = cellZoneNumber;
  }

  /**
   * Returns the variant of the game associated with this move record.
   *
   * @return The game variant.
   */
  public String getVariant() {
    return variant;
  }

  /**
   * Sets the variant of the game for this move record.
   *
   * @param variant The game variant to set.
   */
  public void setVariant(String variant) {
    this.variant = variant;
  }

  /**
   * Returns the number of players involved in the game for this move record.
   *
   * @return The number of players.
   */
  public int getNumOfPlayers() {
    return numOfPlayers;
  }

  /**
   * Sets the number of players involved in the game for this move record.
   *
   * @param numOfPlayers The number of players to set.
   */
  public void setNumOfPlayers(int numOfPlayers) {
    this.numOfPlayers = numOfPlayers;
  }
}
