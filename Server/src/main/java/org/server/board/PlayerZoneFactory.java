package org.server.board;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Factory class for generating and managing player zones on the game board.
 */
public class PlayerZoneFactory {

  private static int numPlayers;
  private static int boardWidth;
  private static int boardHeight;
  private static int playerZoneHeight;
  private static int numOfCellsPerZone;
  private static int[][] playerZonesStartPoints;
  private static ArrayList<Integer> finishedPlayers;
  private static int seed;
  private static int[] activeZoneNums;
  private static int[] yinyangPlayerZoneNums;

  private static final Map<Integer, Map<Integer, Integer>> OPPOSITE_TARGET_PLAYER_NUMBERS = Map.of(
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

  private static final Map<Integer, Map<Integer, Integer>> TARGET_PLAYER_NUMBERS = Map.of(
      2, Map.of(
          1, 1,
          4, 2
      ),
      3, Map.of(
          1, 1,
          3, 2,
          5, 3
      ),
      4, Map.of(
          2, 1,
          3, 2,
          5, 3,
          6, 4
      ),
      6, Map.of(
          1, 1,
          2, 2,
          3, 3,
          4, 4,
          5, 5,
          6, 6
      )
  );

  /**
   * Constructs a PlayerZoneFactory object.
   *
   * @param numOfPlayers The number of players.
   * @param boardWidthValue The width of the board.
   * @param boardHeightValue The height of the board.
   * @param playerZoneHeightValue The height of each player's zone.
   */
  public PlayerZoneFactory(int numOfPlayers, int boardWidthValue, int boardHeightValue, int playerZoneHeightValue) {
    numPlayers = numOfPlayers;
    playerZoneHeight = playerZoneHeightValue;
    boardWidth = boardWidthValue;
    boardHeight = boardHeightValue;
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
    yinyangPlayerZoneNums = new int[2];
    activeZoneNums = new int[6];
    markActiveZones();
  }

  /**
   * Marks active zones on the board based on the number of players.
   */
  private void markActiveZones() {
    for (int i = 0; i < activeZoneNums.length; i++) {
      activeZoneNums[0] = 0;
    }
    switch (numPlayers) {
      case 2: activeZoneNums[0] = activeZoneNums[3] = 1; break;
      case 3: activeZoneNums[0] = activeZoneNums[2] = activeZoneNums[4] = 1; break;
      case 4: activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[4] = activeZoneNums[5] = 1; break;
      default: activeZoneNums[0] = activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[3] = activeZoneNums[4] = activeZoneNums[5] = 1;
    }
  }

  /**
   * Adds player zones to the board.
   *
   * @return The updated board cells with player zones added.
   */
  public static Cell[][] addPlayerZones(Cell[][] cells) {
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
   * Adds player zones to the board for a specified number of players.
   *
   * @param cells The current state of the board's cells.
   * @return The updated board cells with player zones added.
   */
  public Cell[][] addOrderPlayerZones(Cell[][] cells) {
    int marbles = numOfCellsPerZone;

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
            setOrderCellZone(i + 1, playerNum, cells[row][col]);
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
            setOrderCellZone(i + 1, playerNum, cells[row][col]);
          }
          k++;
        }
      }

      if (playerNum == defaultPlayerNum) {
        defaultPlayerNum++;
      }
    }

    // Randomly distribute pawns in the middle of the board.
    System.out.println(seed);
    Random random = new Random(seed);
    int player = 0;
    for (int j : activeZoneNums) {
      if (j!=0) {
        player++;
        int i = 0;
        while (i < marbles) {
          int row = random.nextInt(boardHeight);
          int col = random.nextInt(boardWidth);

          if (cells[row][col].getPawn() == null && cells[row][col].getZoneNum()==0 && cells[row][col].isInsideBoard()) {
            Pawn pawn = new Pawn(player, cells[row][col]);
            cells[row][col].pawnMoveIn(pawn);
            System.out.println("row: " + row + ", col: " + col + ", isOccupied: " + cells[row][col].isOccupied());
            i++;
          }
        }
      }
    }

    return cells;
  }

  /**
   * Randomly selects two zones for Yin and Yang gameplay mode and assigns players to them.
   *
   * @param cells The current state of the board's cells.
   * @return The updated board cells with Yin and Yang zones added.
   */
  public Cell[][] addYinYangZones(Cell[][] cells) {
    Random random = new Random(seed);

    for (int i = 0; i < activeZoneNums.length; i++) {
      activeZoneNums[i] = 0;
    }

    int player1ZoneNum = random.nextInt(6);
    int player2ZoneNum = random.nextInt(6);
    while (player1ZoneNum == player2ZoneNum) {
      player2ZoneNum = random.nextInt(6);
    }
    System.out.println("zone1: " + player1ZoneNum);
    System.out.println("zone2: " + player2ZoneNum);

    yinyangPlayerZoneNums[0] = player1ZoneNum;
    yinyangPlayerZoneNums[1] = player2ZoneNum;

    activeZoneNums[player1ZoneNum] = activeZoneNums[player2ZoneNum] = 1;
    int playerNum;

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i % 2 == 0) {
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            if (activeZoneNums[i] == 1) {
              if (i == player1ZoneNum) {
                playerNum = 1;
              } else {
                playerNum = 2;
              }
              setCellZone(playerNum,i + 1,  cells[row][col]);
            }
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            if (activeZoneNums[i] == 1) {
              if (i == player1ZoneNum) {
                playerNum = 1;
              } else {
                playerNum = 2;
              }
              setCellZone(playerNum,i + 1,  cells[row][col]);
            }
          }
          k++;
        }
      }
    }

    return cells;
  }

  /**
   * Configures a cell with player and zone information, and optionally places a pawn.
   *
   * @param playerNum The player's number (0 if no player, otherwise the player's number).
   * @param zoneNum The number of the zone being assigned.
   * @param currentCell The cell to configure with player and zone data.
   */
  public static void setCellZone(int playerNum, int zoneNum, Cell currentCell) {
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneNum(zoneNum);
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum,currentCell);
      currentCell.pawnMoveIn(pawn);
    }
  }

  /**
   * Configures a cell for order out of chaos variant, with player and zone information, and optionally places a pawn.
   *
   * @param playerNum The player's number (0 if no player, otherwise the player's number).
   * @param zoneNum The number of the zone being assigned.
   * @param currentCell The cell to configure with player and zone data.
   */
  private static void setOrderCellZone(int zoneNum, int playerNum, Cell currentCell) {
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneNum(zoneNum);
  }

  /**
   * Retrieves the player number corresponding to the target zone in a standard game.
   *
   * @param zoneNum The zone number for which to retrieve the target player number.
   * @return The player number of the target zone.
   */
  public static int getTargetPlayerNum(int zoneNum) {
    return TARGET_PLAYER_NUMBERS.get(numPlayers).getOrDefault(zoneNum, 0);
  }

  /**
   * Retrieves the player number corresponding to the opposite target zone in a standard game.
   *
   * @param zoneNum The zone number for which to retrieve the opposite target player number.
   * @return The player number of the opposite target zone.
   */
  public static int getOppositeTargetPlayerNum(int zoneNum) {
    return OPPOSITE_TARGET_PLAYER_NUMBERS.get(numPlayers).getOrDefault(zoneNum, 0);
  }

  /**
   * Determines the number of cells in a player zone based on its height.
   *
   * @param playerZoneHeight The height of the player's zone.
   * @return The total number of cells in the zone.
   */
  private int getNumOfCellsPerZone(int playerZoneHeight) {
    int counter = playerZoneHeight;
    int numOfCells = 0;
    while (counter != 0) {
      numOfCells += counter;
      counter--;
    }
    return numOfCells;
  }

  /**
   * Checks if a player has won the game based on the current board state and variant rules.
   *
   * @param cells The current state of the board's cells.
   * @param variant The variant of the game (e.g., "standard", "order", or "yin-yang").
   * @return The player number of the winner (0 if no winner yet).
   */
  public static int checkZoneForWin(Cell[][] cells, String variant) {
    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];


      int targetPlayerNum;
      switch (variant) {
        case "standard": targetPlayerNum = getOppositeTargetPlayerNum(i + 1); break;
        case "order": targetPlayerNum = getTargetPlayerNum(i + 1); break;
        default:
          if (i == yinyangPlayerZoneNums[0]) {
            targetPlayerNum = 2;
          } else if (i == yinyangPlayerZoneNums[1]) {
           targetPlayerNum = 1;
          } else {
            targetPlayerNum = 0;
          }
      }

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

  /**
   * Sets the seed for random number generation to ensure repeatability in gameplay.
   *
   * @param seed The seed value to use for random number generation.
   */
  public static void setSeed(int seed) {
    PlayerZoneFactory.seed = seed;
  }
}
