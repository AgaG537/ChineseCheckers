package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Factory class for generating player zones on the board.
 */
public class PlayerZoneFactory {

  /**
   * Generates a default color based on the player's number.
   *
   * @param playerNum The player's number.
   * @return The default color for the player.
   */
  private static Color generateDefaultColor(int playerNum) {
    switch (playerNum) {
      case 1:
        return Color.BLACK;
      case 2:
        return Color.RED;
      case 3:
        return Color.GREEN;
      case 4:
        return Color.WHITE;
      case 5:
        return Color.YELLOW;
      case 6:
        return Color.BLUE;
    }
    return Color.GREY;
  }
  /**
   * Determines if the player number is odd (ascending order of the player zone).
   *
   * @param playerNumber The player's number.
   * @return True if the player number is odd, false otherwise.
   */
  private static boolean isAsc(int playerNumber) {
    return playerNumber == 1 || playerNumber == 3 || playerNumber == 5;
  }

  /**
   * Adds player zones to the board for a specified number of players.
   *
   * @param numPlayers The number of players.
   * @param boardWidth The width of the board.
   * @param boardHeight The height of the board.
   * @param playerZoneHeight The height of each player's zone.
   * @param cells The current state of the board's cells.
   * @return The updated board cells with player zones added.
   */
  public static Cell[][] addPlayerZones(int numPlayers, int boardWidth, int boardHeight, int playerZoneHeight, Cell[][] cells) {
    int[][] playerZonesStartPoints = {
        {0, (boardWidth / 2)}, // Upper zone [0]
        {playerZoneHeight * 2 - 1, boardWidth - playerZoneHeight},  // Right upper zone xxx [1]
        {boardHeight - playerZoneHeight * 2, boardWidth - playerZoneHeight}, // Right bottom zone [2]
        {boardHeight - 1, (boardWidth / 2)}, // Bottom zone xxx [3]
        {boardHeight - playerZoneHeight * 2, playerZoneHeight - 1}, // Left bottom zone [4]
        {playerZoneHeight * 2 - 1, playerZoneHeight - 1} // Left upper zone xxx [5]
    };
    switch (numPlayers) {
      case 2:
        // player num 1 upper zone
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[0][0],playerZonesStartPoints[0][1], generateDefaultColor(1),playerZoneHeight,1,cells,isAsc(1));
        //player num 4 bottom zone
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[3][0],playerZonesStartPoints[3][1], generateDefaultColor(4),playerZoneHeight,4,cells,isAsc(4));
      break;
      case 3:
        // player num 1 upper zone
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[0][0],playerZonesStartPoints[0][1], generateDefaultColor(1),playerZoneHeight,1,cells,isAsc(1));
        // player num 3 right bottom
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[2][0],playerZonesStartPoints[2][1], generateDefaultColor(3),playerZoneHeight,3,cells,isAsc(3));
        // player num 5 left bottom
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[4][0],playerZonesStartPoints[4][1], generateDefaultColor(4),playerZoneHeight,4,cells,isAsc(4));
      break;
      case 4:
        // player num 2 right upper
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[1][0],playerZonesStartPoints[1][1], generateDefaultColor(2),playerZoneHeight,2,cells,isAsc(2));
        // player num 3 right bottom
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[2][0],playerZonesStartPoints[2][1], generateDefaultColor(3),playerZoneHeight,3,cells,isAsc(3));
        // player num 5 left bottom
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[4][0],playerZonesStartPoints[4][1], generateDefaultColor(5),playerZoneHeight,5,cells,isAsc(5));
        // player num 6 left upper
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[5][0],playerZonesStartPoints[5][1], generateDefaultColor(6),playerZoneHeight,6,cells,isAsc(6));
      break;
      case 6:
        // player num 1 upper zone
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[0][0],playerZonesStartPoints[0][1], generateDefaultColor(1),playerZoneHeight,1,cells,isAsc(1));
        // player num 2 right upper
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[1][0],playerZonesStartPoints[1][1], generateDefaultColor(2),playerZoneHeight,2,cells,isAsc(2));
        // player num 3 right bottom
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[2][0],playerZonesStartPoints[2][1], generateDefaultColor(3),playerZoneHeight,3,cells,isAsc(3));
        //player num 4 bottom zone
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[3][0],playerZonesStartPoints[3][1], generateDefaultColor(4),playerZoneHeight,4,cells,isAsc(4));
        // player num 5 left bottom
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[4][0],playerZonesStartPoints[4][1], generateDefaultColor(5),playerZoneHeight,5,cells,isAsc(5));
        // player num 6 left upper
        cells = PlayerZone.addPlayerZone(playerZonesStartPoints[5][0],playerZonesStartPoints[5][1], generateDefaultColor(6),playerZoneHeight,6,cells,isAsc(6));
        break;
    }
    return cells;
  }


}
