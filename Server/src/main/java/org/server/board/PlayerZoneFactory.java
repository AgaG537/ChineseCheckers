package org.server.board;


import java.util.ArrayList;
import java.util.Map;

/**
 * Factory class for generating player zones on the board.
 */
public class PlayerZoneFactory {

  private static int numPlayers;
  private static int playerZoneHeight;
  private static int numOfCellsPerZone;
  private static int[][] playerZonesStartPoints;
  private static ArrayList<Integer> finishedPlayers;

  private static final Map<Integer, Map<Integer, Integer>> TARGET_PLAYER_NUMBERS = Map.of(
      2, Map.of(
          1, 2,
          4, 1
      ),
      3, Map.of(
          2, 3,
          6, 2,
          4, 1
      ),
      4, Map.of(
          3, 4,
          2, 3,
          6, 2,
          5, 1
      ),
      6, Map.of(
          3, 6,
          2, 5,
          1, 4,
          6, 3,
          5, 2,
          4, 1
      )
  );

  /**
   * Constructs a PlayerZoneFactory object.
   *
   * @param numOfPlayers The number of players.
   * @param boardWidth The width of the board.
   * @param boardHeight The height of the board.
   * @param playerZoneHeightValue The height of each player's zone.
   */
  public PlayerZoneFactory(int numOfPlayers, int boardWidth, int boardHeight, int playerZoneHeightValue) {
    numPlayers = numOfPlayers;
    playerZoneHeight = playerZoneHeightValue;
    numOfCellsPerZone = getNumOfCellsPerZone(playerZoneHeight);
    playerZonesStartPoints = new int[][]{
        {0, (boardWidth / 2)}, // Upper zone [0]
        {playerZoneHeight * 2 - 1, boardWidth - playerZoneHeight},  // Right upper zone xxx [1]
        {boardHeight - playerZoneHeight * 2, boardWidth - playerZoneHeight}, // Right bottom zone [2]
        {boardHeight - 1, (boardWidth / 2)}, // Bottom zone xxx [3]
        {boardHeight - playerZoneHeight * 2, playerZoneHeight - 1}, // Left bottom zone [4]
        {playerZoneHeight * 2 - 1, playerZoneHeight - 1} // Left upper zone xxx [5]
    };
    finishedPlayers = new ArrayList<>();
  }

  /**
   * Adds player zones to the board.
   *
   * @return The updated board cells with player zones added.
   */
  public Cell[][] addPlayerZones(Cell[][] cells) {
    int[] activeZoneNums = new int[6];
    for (int i = 0; i < activeZoneNums.length; i++) {
      activeZoneNums[0] = 0;
    }
    switch (numPlayers) {
      case 2: activeZoneNums[0] = activeZoneNums[3] = 1; break;
      case 3: activeZoneNums[0] = activeZoneNums[2] = activeZoneNums[4] = 1; break;
      case 4: activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[4] = activeZoneNums[5] = 1; break;
      default: activeZoneNums[0] = activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[3] = activeZoneNums[4] = activeZoneNums[5] = 1;
    }

    int defaultPlayerNum = 1;
    int playerNum = 0;

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i % 2 == 0) {
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setCellZone(playerNum, i + 1, cells[row][col]);
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setCellZone(playerNum, i + 1, cells[row][col]);
          }
          k++;
        }
      }
      if (playerNum == defaultPlayerNum) {
        defaultPlayerNum++;
      }

    }

    return cells;
  }

  /**
   * Sets the initial player number and color for a given cell, and places a pawn if the player number is non-zero.
   *
   * @param playerNum The player's number (0 if no player, otherwise the player's number).
   * @param currentCell The cell to update with the player's information.
   */
  public void setCellZone(int playerNum, int zoneNum, Cell currentCell) {
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneNum(zoneNum);
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum,currentCell);
    }
  }


  public static int checkZoneForWin(Cell[][] cells) {
    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int targetPlayerNum = getTargetPlayerNum(i + 1);

      if (targetPlayerNum != 0 && !finishedPlayers.contains(targetPlayerNum)) {
        int targetPlayerInCount = 0;
        int k = 0;
        if (i % 2 == 0) {
          for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
            for (int col = colStart - k; col <= colStart + k; col += 2) {
              if (cells[row][col].getPawn() != null) {
                if (cells[row][col].getPawn().getPlayerNum() == targetPlayerNum) {
                  targetPlayerInCount++;
                }
              }
            }
            k++;
          }
        } else {
          for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
            for (int col = colStart - k; col <= colStart + k; col += 2) {
              if (cells[row][col].getPawn() != null) {
                if (cells[row][col].getPawn().getPlayerNum() == targetPlayerNum) {
                  targetPlayerInCount++;
                }
              }
            }
            k++;
          }
        }

        if (targetPlayerInCount == numOfCellsPerZone) {
          finishedPlayers.add(targetPlayerNum);
          return targetPlayerNum;
        }
      }

    }

    return 0;
  }

  public static int getTargetPlayerNum(int zoneNum) {
    return TARGET_PLAYER_NUMBERS.get(numPlayers).getOrDefault(zoneNum, 0);
  }

  private int getNumOfCellsPerZone(int playerZoneHeight) {
    int counter = playerZoneHeight;
    int numOfCells = 0;
    while (counter != 0) {
      numOfCells += counter;
      counter--;
    }
    return numOfCells;
  }

}
